# PowerPoint Clone - Java Swing Presentation Editor

A feature-rich presentation editor built with Java Swing that mimics core PowerPoint functionality. Create, edit, and present slides with text boxes, images, drawing tools, and customizable formatting options.

Developed as a GUI application demonstrating advanced Java Swing components, event handling, file I/O with JSON serialization, and custom component design.

---

## Features

### Slide Editing
- Drag-and-drop positioning for all slide elements
- Real-time visual feedback with selection borders
- Multi-threaded UI for smooth interactions
- Dynamic slide canvas with automatic layout management
- Notes panel for speaker annotations

### Text Management
- Insert and position text boxes anywhere on slides
- Right-click context menu for formatting options
- Custom font chooser dialog (family, style, size)
- Color picker for text customization
- Auto-resizing text boxes based on content
- Text justification (left, center, right)
- Subscript and superscript support
- Bold, italic, and underline formatting

### Image Handling
- Insert images from file system via file chooser
- Drag images to reposition on slides
- Manual resize controls with width/height spinners
- Maintains aspect ratio options
- Supports common image formats (PNG, JPEG)
- Image serialization with file path references

### Drawing Tools
- Freehand pencil drawing mode
- Geometric shapes: lines, circles, squares
- Click-and-drag shape creation
- Drag existing shapes to reposition
- Eraser tool to remove drawn elements
- Real-time shape preview during creation
- Path-based drawing for smooth curves

### File Operations
- Save presentations to JSON format
- Open existing presentations with full state restoration
- Save As functionality for creating copies
- Automatic serialization of:
  - Text box positions, fonts, colors, and content
  - Image positions, dimensions, and file paths
  - All drawing elements (future enhancement)
- Create new blank presentations

### User Interface
- Vertical menu bar with categorized options
- File, Edit, Home, Insert, Tools, Present, Help menus
- Resize controls with increment/decrement spinners
- Checkbox toggles for width/height resizing
- Startup screen for project management
- Modal dialogs for font and color selection

---

## Project Structure

- `Driver.java` — Application entry point, launches startup screen
- `StartupScreen.java` — Initial window for creating/opening presentations
- `EditMenu.java` — Main editor window (JFrame) containing slide canvas and controls
- `Slide.java` — Custom JPanel for slide content with drawing capabilities
- `DraggablePanel.java` — Base class for draggable UI components
- `SlideTextbox.java` — Editable text box component extending DraggablePanel
- `SlideImage.java` — Image component with resize functionality
- `SideMenu.java` — Vertical menu bar with all menu items and action listeners
- `VerticalMenuBar.java` — Custom JMenuBar with vertical grid layout
- `TextAttributes.java` — Popup menu for text formatting (font, color)
- `JFontChooser.java` — Custom font selection dialog
- `Notes.java` — Speaker notes panel component
- `PresentingView.java` — Presentation mode view (future enhancement)
- `ToolMenu.java` — Additional tools menu (placeholder)
- `ExpandableButtonDrop.java` — Expandable dropdown button component (utility)

---

## Requirements

Java Development Kit (JDK) 8 or newer

External Libraries:
- org.json (JSON parsing and serialization)

Standard Java Libraries:
- javax.swing (GUI components)
- java.awt (Graphics and event handling)
- java.io (File operations)
- javax.imageio (Image loading)

---

## Build and Run Instructions

1. Clone the repository:
   git clone https://github.com/acetrow/presentation-software.git
   cd presentation-software

2. Ensure JDK is installed:
   java -version

3. Download org.json library:
   Download json-20240303.jar (or latest) from https://mvnrepository.com/artifact/org.json/json
   Place in project lib/ directory

4. Compile the project:
   javac -cp ".:lib/json-20240303.jar" *.java

   On Windows:
   javac -cp ".;lib/json-20240303.jar" *.java

5. Run the application:
   java -cp ".:lib/json-20240303.jar" Driver

   On Windows:
   java -cp ".;lib/json-20240303.jar" Driver

---

## Usage

### Creating a New Presentation

1. Launch the application using Driver.java
2. Click "Create new presentation" on startup screen
3. The main editor window opens with blank slide canvas

### Adding Text Boxes

1. Navigate to Insert → Insert Text Box
2. A text box appears at default position (20, 20)
3. Click and drag to reposition
4. Click inside to edit text content
5. Right-click for formatting options:
   - Change Font (opens font chooser)
   - Change Colour (opens color picker)

### Inserting Images

1. Navigate to Insert → Insert Image
2. Select image file from file chooser dialog
3. Image appears on slide with default dimensions
4. Click and drag to reposition
5. Use resize controls to adjust dimensions:
   - Click + button to enlarge
   - Click - button to shrink
   - Set increment value with spinner
   - Toggle Width/Height checkboxes

### Using Drawing Tools

Lines:
1. Navigate to Tools → Insert Line
2. Click and drag to create line
3. Release to finalize

Circles:
1. Navigate to Tools → Insert Circle
2. Click and drag to create circular shape
3. Release to finalize

Squares:
1. Navigate to Tools → Insert Square
2. Click and drag to create square
3. Maintains equal width/height ratio

Freehand Drawing:
1. Navigate to Tools → Draw
2. Click and drag to draw freehand paths
3. Click Draw again to toggle off

Eraser:
1. Navigate to Tools → Erase
2. Click and drag over elements to remove
3. Works on lines, circles, squares, and paths

### Saving and Opening

Save:
1. Navigate to File → Save (or Save As for first save)
2. Choose location and filename in file dialog
3. Presentation saved as .json file

Open:
1. Navigate to File → Open
2. Select .json presentation file
3. All text boxes, images, and formatting restored

---

## Implementation Details

### Drag-and-Drop System
- MouseAdapter listeners on DraggablePanel base class
- mousePressed captures initial click coordinates
- mouseDragged calculates offset and updates component location
- All slide elements inherit drag functionality

### JSON Serialization Format

{
  "textBoxes": [
    {
      "xPosition": 20,
      "yPosition": 50,
      "fontName": "Arial",
      "fontStyle": 0,
      "fontSize": 14,
      "content": "Sample text",
      "colorRed": 0,
      "colorGreen": 0,
      "colorBlue": 0
    }
  ],
  "images": [
    {
      "xPosition": 100,
      "yPosition": 100,
      "width": 200,
      "height": 150,
      "path": "/absolute/path/to/image.png"
    }
  ]
}

### Drawing Architecture
- Slide maintains ArrayLists for shapes: lines, circles, squares, paths
- paintComponent() iterates and renders all shapes
- Mouse listeners handle shape creation and manipulation
- Graphics2D for smooth rendering
- Shape selection and dragging via contains() checking

### Event Handling Pattern
- Action listeners attached to menu items
- Events propagate from SideMenu to EditMenu
- EditMenu delegates to Slide for drawing operations
- Observer pattern for UI updates

---

## Known Limitations

- Drawing elements (lines, circles, squares, paths) are not yet serialized
- Presentation mode (PresentingView) is incomplete
- Single slide only - no multi-slide support
- No undo/redo functionality implemented
- Copy/paste features not implemented
- Spell check menu item not functional
- No animation or transition effects

---

## Future Enhancements

- Multi-slide presentations with slide navigation
- Drawing element serialization to JSON
- Full presentation mode with slide show controls
- Undo/redo stack implementation
- Clipboard operations (copy/paste)
- Slide thumbnails panel
- Export to PDF or image formats
- Animation effects and transitions
- Collaborative editing features

---

## License

This project is released under the MIT License.