# 🎮 Garden Stealth Game - Java Presentation

## **Project Overview**
A 2D stealth-based adventure game built in Java using Swing graphics and object-oriented programming principles.

---

## **🎯 Game Concept**
- **Genre**: 2D Stealth Adventure
- **Objective**: Navigate through garden environments, collect keys and chests while avoiding monsters
- **Gameplay**: Multi-level progression with time-based challenges

---

## **🛠️ Technical Architecture**

### **Core Technologies**
- **Language**: Java
- **Graphics**: Java Swing (JFrame, JPanel)
- **Game Loop**: Custom 60 FPS implementation
- **Asset Management**: BufferedImage for sprites

### **Key Components**
```
📁 Project Structure:
├── 🎮 Main Game (gardenstealth1.java)
├── 👤 Player System (player.java)
├── 🗺️ Tile Management (tilemanager.java)
├── 💥 Collision Detection (CollisionChecker_1.java)
├── 🎵 Sound System (sound.java)
├── 🎨 User Interface (UserInterface.java)
└── 🎯 Asset Management (AssetSetter.java)
```

---

## **🎮 Game Features**

### **Player Mechanics**
- **Movement**: 4-directional movement (WASD/Arrow Keys)
- **Collection**: Keys and chests with inventory tracking
- **Stealth**: Avoid monster detection
- **Progression**: Multi-level gameplay

### **Level System**
- **Level 1**: 60-second time limit
- **Level 2**: 120-second time limit with increased difficulty
- **Objectives**: Collect all keys and chests within time limit

### **Game States**
- **Play State**: Active gameplay
- **Pause State**: Game paused
- **Win/Lose Conditions**: Time-based and objective-based

---

## **🎨 Visual Elements**

### **Graphics System**
- **Tile Size**: 16x16 pixels (scaled 3x = 48x48)
- **Screen Resolution**: 768x576 pixels (16x12 tiles)
- **World Size**: 50x50 tiles maximum

### **Assets**
- **Player Sprites**: 8-directional animated character
- **Monster Sprites**: Orc enemies with movement patterns
- **Objects**: Keys, chests, doors, walls
- **Environment**: Grass, trees, walls

---

## **🔧 Technical Implementation**

### **Game Loop Architecture**
```java
// 60 FPS Game Loop
public void run() {
    double drawInterval = 1000000000/FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    
    while(gameThread != null) {
        long currentTime = System.nanoTime();
        delta += (currentTime - lastTime) / drawInterval;
        lastTime = currentTime;
        
        if(delta >= 1) {
            update();
            repaint();
            delta--;
        }
    }
}
```

### **Collision Detection**
- **Rectangle-based collision system**
- **Solid area detection for objects**
- **Monster-player collision handling**

### **Sound Integration**
- **Background music support**
- **Sound effects for interactions**
- **Audio file management (.wav format)**

---

## **🎯 Key Achievements**

### **Programming Concepts Demonstrated**
- ✅ **Object-Oriented Programming**: Classes, inheritance, polymorphism
- ✅ **Game Development**: Game loop, sprite animation, collision detection
- ✅ **User Interface**: Custom UI elements and HUD
- ✅ **File I/O**: Asset loading and map system
- ✅ **Event Handling**: Keyboard input and game state management

### **Advanced Features**
- **Multi-level progression system**
- **Time-based gameplay mechanics**
- **Inventory and collection tracking**
- **Pause/resume functionality**
- **Win/lose condition handling**

---

## **🚀 Future Enhancements**

### **Potential Improvements**
- **Additional levels and environments**
- **Power-ups and special abilities**
- **Score system and leaderboards**
- **Enhanced graphics and animations**
- **Multiplayer functionality**

---

## **📊 Project Statistics**
- **Lines of Code**: 500+ lines across multiple files
- **Classes**: 10+ core classes
- **Assets**: 20+ sprite images and sound files
- **Game States**: 3 main states (play, pause, win/lose)

---

## **🎓 Learning Outcomes**
- **Java Swing Graphics Programming**
- **Game Development Fundamentals**
- **Object-Oriented Design Patterns**
- **Real-time Application Development**
- **Asset Management and Resource Handling**

---

*Developed using Java • Swing Graphics • Object-Oriented Programming* 