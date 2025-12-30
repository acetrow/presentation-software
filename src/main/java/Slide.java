import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;


public class Slide extends JPanel {

    private ArrayList<Line2D.Double> lines = new ArrayList<>();
    private ArrayList<Ellipse2D.Double> circles = new ArrayList<>();
    private ArrayList<Rectangle2D.Double> squares = new ArrayList<>();
    private ArrayList<Path2D.Double> paths = new ArrayList<>();
    private Point startPoint = null;
    private Point endPoint = null;
    private Line2D.Double currentLine = null;
    private Ellipse2D.Double currentCircle = null;
    private Rectangle2D.Double currentSquare = null;
    private Path2D.Double currentPath = null;
    private boolean isDrawingLine = false;
    private boolean isDrawingCircle = false;
    private boolean isDrawingSquare = false;
    private boolean isPencilMode = false;

    private Shape selectedShape = null;
    private Point2D lastMousePoint = null;  

    private boolean isEraserMode = false;



    public Slide() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Mouse listener to handle the start of a shape or path
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                endPoint = startPoint;
                if (isDrawingLine) {
                    currentLine = new Line2D.Double(startPoint, endPoint);
                } else if (isDrawingCircle) {
                    currentCircle = new Ellipse2D.Double(startPoint.x, startPoint.y, 0, 0);
                } else if (isDrawingSquare) {
                    currentSquare = new Rectangle2D.Double(startPoint.x, startPoint.y, 0, 0);
                } else if (isPencilMode) {
                    currentPath = new Path2D.Double();
                    currentPath.moveTo(startPoint.x, startPoint.y);
                }

                super.mousePressed(e); 
                Point2D clickPoint = e.getPoint();
                for (Ellipse2D.Double circle : circles) {
                    if (circle.contains(clickPoint)) {
                        selectedShape = circle;
                        lastMousePoint = clickPoint;
                        return; 
                    }
                }
                for (Rectangle2D.Double square : squares) {
                    if (square.contains(clickPoint)) {
                        selectedShape = square;
                        lastMousePoint = clickPoint;
                        return;
                    }
                }
                for (Line2D.Double line : lines) {
                    if (isNearLine(clickPoint, line)) {
                        selectedShape = line;
                        lastMousePoint = clickPoint;
                        return; 
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (currentLine != null && isDrawingLine) {
                    lines.add(currentLine);
                    currentLine = null;
                    isDrawingLine = false;
                } else if (currentCircle != null && isDrawingCircle) {
                    circles.add(currentCircle);
                    currentCircle = null;
                    isDrawingCircle = false;
                } else if (currentSquare != null && isDrawingSquare) {
                    squares.add(currentSquare);
                    currentSquare = null;
                    isDrawingSquare = false;
                } else if (currentPath != null && isPencilMode) {
                    paths.add(currentPath);
                    currentPath = null;
                }
                super.mouseReleased(e); 
                selectedShape = null;
                lastMousePoint = null;
                repaint();
            }
        });

        // Mouse motion listener to track dragging and drawing
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (isEraserMode) {
                    Point2D dragPoint = e.getPoint();
                    
                    //check and remove circles
                    circles.removeIf(circle -> circle.contains(dragPoint));
                    
                    //check and remove squares
                    squares.removeIf(square -> square.contains(dragPoint));
                    
                    //check and remove line
                    lines.removeIf(line -> isNearLine(dragPoint, line));

                    //check and remove free draw path if its close to the mouse cursor
                    paths.removeIf(path -> path.intersects(dragPoint.getX() - 5, dragPoint.getY() - 5, 10, 10));
                    
                    repaint();
                    return;
                }

                endPoint = e.getPoint();
                if (currentLine != null && isDrawingLine) {
                    currentLine.setLine(startPoint, endPoint);
                } else if (currentCircle != null && isDrawingCircle) {
                    currentCircle.setFrameFromDiagonal(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
                } else if (currentSquare != null && isDrawingSquare) {
                    double size = Math.max(Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
                    currentSquare.setRect(startPoint.x, startPoint.y, size, size);
                } else if (currentPath != null && isPencilMode) {
                    currentPath.lineTo(endPoint.x, endPoint.y);
                }
                super.mouseDragged(e);//to drag the shapes around
                if (selectedShape != null && lastMousePoint != null) {
                    double dx = e.getX() - lastMousePoint.getX();
                    double dy = e.getY() - lastMousePoint.getY();

                    if (selectedShape instanceof Ellipse2D.Double) {
                        Ellipse2D.Double circle = (Ellipse2D.Double) selectedShape;
                        circle.x += dx;
                        circle.y += dy;
                    }
                    if (selectedShape instanceof Rectangle2D.Double) {
                        Rectangle2D.Double square = (Rectangle2D.Double) selectedShape;
                        square.x += dx;
                        square.y += dy;
                    }
                    if (selectedShape instanceof Line2D.Double) {
                        Line2D.Double line = (Line2D.Double) selectedShape;
                        double newX1 = line.getX1() + dx;
                        double newY1 = line.getY1() + dy;
                        double newX2 = line.getX2() + dx;
                        double newY2 = line.getY2() + dy;
                        line.setLine(newX1, newY1, newX2, newY2);
                    }
                    

                    lastMousePoint = e.getPoint(); //update last mouse position
                    repaint(); //repaint to show the updated position
                }
                repaint();
                
            }
        });
    }

    public void enableDrawingLine() {
        isDrawingLine = true;
    }

    public void enableDrawingCircle() {
        isDrawingCircle = true;
    }

    public void enableDrawingSquare() {
        isDrawingSquare = true;
    }

    public void togglePencilMode() {
        isPencilMode = !isPencilMode; //toggle pencil mode
        if (!isPencilMode) {
            currentPath = null; //end current path if pencil mode is turned off
        }
    }
    
    private boolean isNearLine(Point2D p, Line2D.Double line) {
        final double threshold = 5.0; //defining how close the click needs to be to the line
        return line.ptSegDist(p) <= threshold;
    }
    
    public void toggleEraserMode() {
        isEraserMode = !isEraserMode;
        // reset other modes to prevent drawing while erasing
        isDrawingLine = false;
        isDrawingCircle = false;
        isDrawingSquare = false;
        isPencilMode = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Line2D.Double line : lines) {
            g2.draw(line);
        }
        for (Ellipse2D.Double circle : circles) {
            g2.draw(circle);
        }
        for (Rectangle2D.Double square : squares) {
            g2.draw(square);
        }
        if (currentLine != null) {
            g2.draw(currentLine);
        }
        if (currentCircle != null) {
            g2.draw(currentCircle);
        }
        if (currentSquare != null) {
            g2.draw(currentSquare);
        }


        for (Path2D.Double path : paths) {
            g2.draw(path);
        }
        if (currentPath != null) {
            g2.draw(currentPath);
        }
    }
}