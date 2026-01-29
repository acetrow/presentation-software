import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;


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
        setLayout(null); // Use null layout for absolute positioning
        setBackground(Color.WHITE);
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

    public void clearDrawings() {
        lines.clear();
        circles.clear();
        squares.clear();
        paths.clear();
        currentLine = null;
        currentCircle = null;
        currentSquare = null;
        currentPath = null;
        repaint();
    }

    public JSONObject exportDrawingsToJson() {
        JSONObject out = new JSONObject();

        JSONArray jLines = new JSONArray();
        for (Line2D.Double l : lines) {
            JSONObject o = new JSONObject();
            o.put("x1", l.getX1());
            o.put("y1", l.getY1());
            o.put("x2", l.getX2());
            o.put("y2", l.getY2());
            jLines.put(o);
        }
        out.put("lines", jLines);

        JSONArray jCircles = new JSONArray();
        for (Ellipse2D.Double c : circles) {
            JSONObject o = new JSONObject();
            o.put("x", c.getX());
            o.put("y", c.getY());
            o.put("w", c.getWidth());
            o.put("h", c.getHeight());
            jCircles.put(o);
        }
        out.put("circles", jCircles);

        JSONArray jSquares = new JSONArray();
        for (Rectangle2D.Double r : squares) {
            JSONObject o = new JSONObject();
            o.put("x", r.getX());
            o.put("y", r.getY());
            o.put("w", r.getWidth());
            o.put("h", r.getHeight());
            jSquares.put(o);
        }
        out.put("squares", jSquares);

        // Paths as list of points (flattened)
        JSONArray jPaths = new JSONArray();
        for (Path2D.Double p : paths) {
            JSONArray pts = new JSONArray();
            PathIterator it = p.getPathIterator(null, 1.0);
            double[] coords = new double[6];
            while (!it.isDone()) {
                int type = it.currentSegment(coords);
                if (type == PathIterator.SEG_MOVETO || type == PathIterator.SEG_LINETO) {
                    JSONObject pt = new JSONObject();
                    pt.put("x", coords[0]);
                    pt.put("y", coords[1]);
                    pts.put(pt);
                }
                it.next();
            }
            jPaths.put(pts);
        }
        out.put("paths", jPaths);

        return out;
    }

    public void importDrawingsFromJson(JSONObject drawings) {
        clearDrawings();
        if (drawings == null) return;

        JSONArray jLines = drawings.optJSONArray("lines");
        if (jLines != null) {
            for (int i = 0; i < jLines.length(); i++) {
                JSONObject o = jLines.getJSONObject(i);
                lines.add(new Line2D.Double(o.getDouble("x1"), o.getDouble("y1"), o.getDouble("x2"), o.getDouble("y2")));
            }
        }

        JSONArray jCircles = drawings.optJSONArray("circles");
        if (jCircles != null) {
            for (int i = 0; i < jCircles.length(); i++) {
                JSONObject o = jCircles.getJSONObject(i);
                circles.add(new Ellipse2D.Double(o.getDouble("x"), o.getDouble("y"), o.getDouble("w"), o.getDouble("h")));
            }
        }

        JSONArray jSquares = drawings.optJSONArray("squares");
        if (jSquares != null) {
            for (int i = 0; i < jSquares.length(); i++) {
                JSONObject o = jSquares.getJSONObject(i);
                squares.add(new Rectangle2D.Double(o.getDouble("x"), o.getDouble("y"), o.getDouble("w"), o.getDouble("h")));
            }
        }

        JSONArray jPaths = drawings.optJSONArray("paths");
        if (jPaths != null) {
            for (int i = 0; i < jPaths.length(); i++) {
                JSONArray pts = jPaths.getJSONArray(i);
                if (pts.length() == 0) continue;
                Path2D.Double path = new Path2D.Double();
                for (int j = 0; j < pts.length(); j++) {
                    JSONObject pt = pts.getJSONObject(j);
                    double x = pt.getDouble("x");
                    double y = pt.getDouble("y");
                    if (j == 0) path.moveTo(x, y);
                    else path.lineTo(x, y);
                }
                paths.add(path);
            }
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw shapes first (behind components)
        for (Line2D.Double line : lines) {
            g2.draw(line);
        }
        for (Ellipse2D.Double circle : circles) {
            g2.draw(circle);
        }
        for (Rectangle2D.Double square : squares) {
            g2.draw(square);
        }
        for (Path2D.Double path : paths) {
            g2.draw(path);
        }
        
        // Draw current shape being drawn
        if (currentLine != null) {
            g2.draw(currentLine);
        }
        if (currentCircle != null) {
            g2.draw(currentCircle);
        }
        if (currentSquare != null) {
            g2.draw(currentSquare);
        }
        if (currentPath != null) {
            g2.draw(currentPath);
        }
    }
    
    @Override
    public void paintChildren(Graphics g) {
        // Ensure child components (text boxes, images) are painted on top of shapes
        super.paintChildren(g);
    }
}