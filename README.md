# Task Manager

### Prerquisite
Java >= 8
Maven 

### Basic functionality
`TaskManager` has basic methods
 - `addProcess()`
 - `killAll()`
 - `killGroup(priority)`
 - `list()`  

### Supported behaviours
 - `default`: adds process with supplied capacity.
 - `FIFO`: adds process with FIFO manner.  
 - `Priority FIFO`: adds process with Priority FIFO manner.  

### Calling TaskManager
 - `TaskManager.getPriorityBasedTaskManager()`
 - `TaskManager.getFifoBasedTaskManager()`
 - `TaskManager.getDefaultTaskManager()`