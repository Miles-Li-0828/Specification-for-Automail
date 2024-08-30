**README for Automail System Implementation**

---

### Overview

This repository contains the implementation and design analysis of the Automail System, an automated mail sorting and delivery system designed for large buildings with a dedicated mailroom. The system was originally developed to deliver letters using a single operational mode, but has since been refactored and extended to support parcel delivery and the new FLOORING mode, which introduces more specialized robot behaviors.

---

### Project Structure

- **/src**: Contains the source code for the Automail System.
- **/docs**: Documentation, including design analysis, UML diagrams, and sequence diagrams.
- **/tests**: Unit tests for various components of the system.

---

### Key Components

1. **Building Class**:
   - The Building class is a Singleton, ensuring that there is only one instance of the building throughout the system. This provides a global access point and maintains consistency across the system.

2. **MailRoom Class**:
   - Manages mail items after their arrival at the building's mailroom. The MailRoom class is responsible for deciding the order in which mail items should be delivered.

3. **RobotController**:
   - Acts as an intermediary between robots and the building. It manages the responsibilities that were previously spread across the Building and MailRoom classes, leading to improved cohesion.

4. **Robot Types**:
   - **CyclingRobot**: Operates in the original cycling mode.
   - **FloorRobot**: Operates within a specific floor in the new FLOORING mode.
   - **ColumnRobot**: Operates within specific columns in the new FLOORING mode.

5. **Simulation Class**:
   - Simulates the operations of the mailroom and robots based on a given operational mode. It uses the Factory Pattern to instantiate the appropriate RobotController and related robots.

---

### Design Patterns

- **Singleton Pattern**: Used in the Building class to ensure only one instance of the building exists.
- **Factory Pattern**: Employed in the Simulation class to create different types of RobotControllers and robots based on the operational mode.
- **GRASP Principles**:
   - **Pure Fabrication**: The RobotController class is introduced to reduce direct coupling between classes.
   - **Creator**: The RobotController is responsible for creating robots, and the Simulation class creates Items (Letters and Parcels).
   - **Information Expert**: Each class handles the information it knows best, ensuring high cohesion.

---

### Refactoring & Improvements

1. **Reduced Coupling**:
   - By introducing the RobotController as an intermediary, we reduced direct dependencies between key classes, making the system more maintainable and flexible.

2. **Enhanced Cohesion**:
   - Responsibilities have been reassigned to ensure that each class has a focused role, improving the system's overall cohesion.

3. **Support for New Modes**:
   - The system has been extended to support the FLOORING mode, which introduces more complex robot behaviors. The new design allows for the easy addition of new modes without significant changes to existing code.

---

### Future Development

- **Scalability**:
   - The current design is modular and extendable, allowing for the addition of new robot types or operational modes in the future with minimal changes to the existing codebase.

- **Maintenance**:
   - The use of design patterns like Factory and GRASP principles ensures that the system is maintainable and can be easily modified to meet future requirements.

---

To modify the "How to run" section in your README file, we will update it to reflect the usage of the `Main` class to execute the program. Here's how you can structure that section:

---

### How to Run

To execute the Automail simulation, follow these steps:

1. **Compile the Code**: 
   Ensure all Java files, including `Main.java`, are compiled. You can compile all files using the following command in your terminal:
   
   ```bash
   javac *.java
   ```

2. **Run the Simulation**:
   Once the code is compiled, you can run the simulation by executing the `Main` class. Use the following command:
   
   ```bash
   java Main
   ```
   
   This will start the simulation and execute the delivery process based on the configuration provided in the code.

3. **Simulation Output**:
   The output of the simulation will be printed in the console, showing the sequence of events as the robots deliver the items according to the specified modes (Cycling or Flooring).

---

### Contact

For any questions or issues, please contact the project contributors:

- **Miles Li**: miles.li@example.com
- **Skylar Khant**: skylar.khant@example.com
- **Ngoc Thanh Lam Nguyen**: nguyen.lam@example.com
