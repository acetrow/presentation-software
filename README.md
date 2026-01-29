# PowerPoint Clone - Java Swing Presentation Editor

A comprehensive presentation editor built with Java Swing that provides PowerPoint-like functionality. Create, edit, and present multi-slide presentations with rich text formatting, images, drawing tools, and a professional user interface.

![Java](https://img.shields.io/badge/Java-19-orange) ![License](https://img.shields.io/badge/License-MIT-blue)

---

## Features

### Multi-Slide Support
- **Add/Remove slides** - Create presentations with multiple slides
- **Slide navigation** - Navigate between slides using buttons or thumbnail panel
- **Slide thumbnails** - Visual preview panel on the left (PowerPoint-style)
- **Slide management** - Each slide maintains its own content, formatting, and drawings
- **Automatic saving** - All slides are saved and restored from JSON format

### Text Editing & Formatting
- **Rich text boxes** - Insert and position text anywhere on slides
- **Font customization** - Choose font family, style (bold, italic), and size
- **Color picker** - Customize text colors
- **Text formatting** - Bold, italic, underline support
- **Auto-resizing** - Text boxes automatically adjust to content
- **Right-click menu** - Quick access to formatting options
- **Drag & drop** - Reposition text boxes by clicking and dragging

### Image Management
- **Image insertion** - Add images from file system
- **Image positioning** - Drag images to reposition on slides
- **Resize controls** - Adjust image dimensions with +/- buttons
- **Format support** - PNG, JPEG, and other standard formats
- **Persistent storage** - Image paths saved in presentation file

### Drawing Tools
- **Geometric shapes** - Draw lines, circles, and squares
- **Freehand drawing** - Pencil tool for freeform sketches
- **Shape manipulation** - Click and drag to move shapes
- **Eraser tool** - Remove drawn elements by dragging over them
- **Real-time preview** - See shapes as you draw them
- **Persistent drawings** - All drawings saved per slide

### Presentation Mode
- **Full-screen mode** - Present slides in full-screen view
- **Slide scaling** - Automatic scaling to fit screen
- **Keyboard controls** - Press ESC to exit presentation mode
- **Live updates** - Changes reflect in presentation view

### File Operations
- **Save presentations** - Save to JSON format with full state preservation
- **Open presentations** - Load existing presentations with all content restored
- **Save As** - Create copies of presentations
- **Backward compatible** - Opens old single-slide format files
- **Complete serialization** - Saves text, images, drawings, backgrounds, and notes

### User Interface
- **Vertical menu bar** - Organized menu system (File, Edit, Home, Insert, Tools, Present, Help)
- **Slide thumbnail panel** - Visual navigation on the left side
- **Speaker notes** - Dedicated panel for presentation notes
- **Resize controls** - Precise object resizing with spinners
- **Startup screen** - Easy project creation and opening
- **Modal dialogs** - Professional font and color selection dialogs

---

## Quick Start

### Prerequisites
- **Java Development Kit (JDK) 19** or newer
- **Maven** (recommended) or manual compilation setup

### Using Maven (Recommended)

```bash
# Clone the repository
git clone https://github.com/acetrow/presentation-software.git
cd presentation-software

# Compile and run
mvn clean compile exec:java -Dexec.mainClass="Driver"

# Or build a JAR
mvn clean package
java -jar target/BytePitch-1.0-SNAPSHOT.jar
```

### Manual Compilation

1. **Ensure JDK is installed:**
   ```bash
   java -version
   ```

2. **Download org.json library:**
   - Download from [Maven Repository](https://mvnrepository.com/artifact/org.json/json)
   - Place `json-20230618.jar` (or latest) in a `lib/` directory

3. **Compile:**
   ```bash
   # Linux/Mac
   cd src/main/java
   javac -cp ".:../../lib/json-20230618.jar" *.java
   
   # Windows
   cd src\main\java
   javac -cp ".;..\..\lib\json-20230618.jar" *.java
   ```

4. **Run:**
   ```bash
   # Linux/Mac
   java -cp ".:../../lib/json-20230618.jar" Driver
   
   # Windows
   java -cp ".;..\..\lib\json-20230618.jar" Driver
   ```

---

## Usage Guide

### Creating a Presentation

1. Launch the application - the startup screen appears
2. Click **"Create new presentation"** or **"Open existing presentation"**
3. The main editor opens with a blank slide

### Working with Slides

- **Add slide:** Click **"+ Slide"** button or go to **Home → New slide**
- **Remove slide:** Click **"- Slide"** button or go to **Home → Remove Slide**
- **Navigate slides:** 
  - Use **<** and **>** buttons in the toolbar
  - Click any thumbnail in the left panel
  - Status shows "Slide X / Y"

### Adding Content

#### Text Boxes
1. Go to **Insert → Insert Text Box**
2. Click and drag to reposition
3. Click inside to edit text
4. **Right-click** for formatting options:
   - Change Font
   - Change Colour
5. Use **Home** menu for bold, italic, underline

#### Images
1. Go to **Insert → Insert Image**
2. Select image file from file chooser
3. Drag to reposition
4. Use resize controls:
   - **+** button to enlarge
   - **-** button to shrink
   - Adjust increment with spinner
   - Toggle Width/Height checkboxes

#### Drawing Tools
- **Lines:** Tools → Insert Line, then click and drag
- **Circles:** Tools → Insert Circle, then click and drag
- **Squares:** Tools → Insert Square, then click and drag
- **Freehand:** Tools → Draw, then click and drag (toggle off by clicking Draw again)
- **Eraser:** Tools → Erase, then drag over elements to remove

### Formatting Text

1. Select a text box (click on it)
2. Use **Home** menu options:
   - **Bold** - Toggle bold formatting
   - **Italic** - Toggle italic formatting
   - **Fonts** - Open font chooser dialog
   - **Font Colour** - Open color picker
   - **Justify Text** - Left, Center, or Right alignment

### Saving and Opening

**Save:**
- **File → Save** (saves to current file)
- **File → Save As** (choose new location)
- Presentations saved as `.json` files

**Open:**
- **File → Open**
- Select a `.json` presentation file
- All slides, content, and formatting are restored

### Presentation Mode

1. Go to **Present → Present**
2. Slides display in full-screen mode
3. Press **ESC** to exit presentation mode

---

## Project Structure

```
presentation-software/
├── src/main/java/
│   ├── Driver.java                 # Application entry point
│   ├── StartupScreen.java          # Initial welcome screen
│   ├── EditMenu.java               # Main editor window with multi-slide support
│   ├── Slide.java                  # Slide canvas with drawing capabilities
│   ├── SlideThumbnailPanel.java    # Thumbnail navigation panel
│   ├── DraggablePanel.java         # Base class for draggable components
│   ├── SlideTextbox.java           # Editable text box component
│   ├── SlideImage.java             # Image component with resize
│   ├── SideMenu.java               # Vertical menu bar with actions
│   ├── VerticalMenuBar.java        # Custom vertical menu layout
│   ├── TextAttributes.java         # Right-click formatting menu
│   ├── JFontChooser.java           # Font selection dialog
│   ├── Notes.java                  # Speaker notes panel
│   └── PresentingView.java         # Full-screen presentation mode
├── pom.xml                         # Maven configuration
└── README.md                       # This file
```

### Key Components

- **EditMenu** - Main application window managing slides, UI layout, and slide snapshots
- **Slide** - Custom JPanel handling drawing, shapes, and component rendering
- **SlideThumbnailPanel** - Left-side panel showing visual slide previews
- **SideMenu** - Menu system with file operations, formatting, and tools
- **SlideSnapshot** - Internal model for saving/loading slide state

---

## Technical Details

### Architecture

- **MVC-like pattern** - Separation between UI (EditMenu, Slide) and data (SlideSnapshot)
- **Event-driven** - Action listeners for menu interactions
- **Component-based** - DraggablePanel base class for reusable components
- **State management** - Slide snapshots capture complete slide state

### JSON Format

Presentations are saved in JSON format (v2) with the following structure:

```json
{
  "version": 2,
  "slides": [
    {
      "backgroundRed": 255,
      "backgroundGreen": 255,
      "backgroundBlue": 255,
      "notes": "Speaker notes here",
      "textBoxes": [
        {
          "xPosition": 20,
          "yPosition": 50,
          "width": 250,
          "height": 100,
          "fontName": "Arial",
          "fontStyle": 1,
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
          "path": "/path/to/image.png"
        }
      ],
      "drawings": {
        "lines": [...],
        "circles": [...],
        "squares": [...],
        "paths": [...]
      }
    }
  ]
}
```

The application is **backward compatible** with old single-slide JSON files.

### Drawing System

- Shapes stored as Java2D objects (Line2D, Ellipse2D, Rectangle2D, Path2D)
- Mouse listeners handle creation and manipulation
- Graphics2D with antialiasing for smooth rendering
- Shape selection via geometric contains() checking
- Drawings serialized to JSON per slide

---

## Features Status

### Implemented
- Multi-slide presentations
- Slide thumbnails panel
- Text formatting (bold, italic, fonts, colors)
- Image insertion and manipulation
- Drawing tools (lines, circles, squares, freehand)
- Eraser tool
- Presentation mode (full-screen)
- Save/Open presentations
- Drawing serialization
- Speaker notes
- Drag-and-drop positioning

### Future Enhancements
- Undo/redo functionality
- Copy/paste operations
- Spell checker
- Table insertion
- Code snippet insertion
- Bullet point lists
- Export to PDF/image formats
- Slide transitions and animations
- Collaborative editing

---

## Known Issues

- Some menu items show "coming soon" messages (undo, redo, copy, paste, etc.)
- Spell check not yet implemented
- No table or code snippet insertion yet

---

## License

This project is released under the **MIT License**.

---

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

---

## Acknowledgments

Built with Java Swing, demonstrating advanced GUI programming concepts, event handling, file I/O, and custom component design.
