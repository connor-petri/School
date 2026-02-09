# Assignment 1 Use Cases

---

## Access Day View
| Step | User | System |
|---|---|---|
| 1 | Inputs v for (V)iew| |
| 2 | | Prompts "(D)ay view or (M)onth?" |
| 3 | Inputs d for (D)ay | |
| 4 | | Prints the day of the week, date, and any events from that day |

## Access Month View
| Step | User | System |
|---|---|---|
| 1 | Inputs v for (View)| |
| 2 | | Prompts "(D)ay view or (M)onth?" |
| 3 | Inputs m for (M)onth | |
| 4 | | Prints the month in the format of a calandar, with the date and days of the week at the top and any day with an event marked with \{\} |
| 5 | | Prompts "(P)revious, (N)ext, or (G)o back?" |

### Access Previous Month
| Step | User | System |
|---|---|---|
| 1 | Performs *Access Month View* | |
| 2 | Inputs p for (P)revious | | |
| 3 | | Prints the previous month in the format of a calandar, with the date and days of the week at the top and any day with an event marked with \{\} |
| 4 | | Prompts "(P)revious, (N)ext, or (G)o back?" |

### Access Next Month
| Step | User | System |
|---|---|---|
| 1 | Performs *Access Month View* | |
| 2 | Inputs n for (N)ext | | |
| 3 | | Prints the next month in the format of a calandar, with the date and days of the week at the top and any day with an event marked with \{\} |
| 4 | | Prompts "(P)revious, (N)ext, or (G)o back?" |

### Go Back to Main Menu from Month View
| Step | User | System |
|---|---|---|
| 1 | Performs *Access Month View* | |
| 2 | Inputs g for (G)o back | | |
| 3 | | Returns to main menu |

---

## Create Event
| Step | User | System |
|---|---|---|
| 1 | Inputs c for (C)reate| |
| 2 | | Prompts and collects "Name:", "Date:", "Start Time:", "End Time:" |

### Create Event with Empty Name
| Step | User | System |
|---|---|---|
| 1 | Performs *Create Event* | |
| 2 | Inputs an empty string for "Name:" | |
| 3 | | Prompts "Event name cannot be empty. Please enter a valid name." |
| 4 | | Returns to previous menu |

### Create Event with Invalid Date
| Step | User | System |
|---|---|---|
| 1 | Performs *Create Event* | |
| 2 | Inputs an invalid date for "Date:" | |
| 3 | | Prompts "Invalid date. Please enter a valid date (MM/DD/YYYY)" |
| 4 | | Returns to previous menu |

### Create Event with Invalid Start Time
| Step | User | System |
|---|---|---|
| 1 | Performs *Create Event* | |
| 2 | Inputs an invalid time for "Start Time:" | |
| 3 | | Prompts "Invalid time. Please enter a valid time (24hrs clock) (HH:MM)" |
| 4 | | Returns to previous menu |

### Create Event with Invalid End Time
| Step | User | System |
|---|---|---|
| 1 | Performs *Create Event* | |
| 2 | Inputs an invalid time for "End Time:" | |
| 3 | | Prompts "Invalid time. Please enter a valid time (24hrs clock) (HH:MM)" |
| 4 | | Returns to previous menu |

### Create Event with End Time Before Start Time
| Step | User | System |
|---|---|---|
| 1 | Performs *Create Event* | |
| 2 | Inputs an end time that is before the start time for "End Time:" | |
| 3 | | Prompts "End time cannot be before start time. Please enter a valid end time (24hrs clock) (HH:MM)" |
| 4 | | Returns to previous menu |

### Create Event with Time Conflict
| Step | User | System |
|---|---|---|
| 1 | Performs *Create Event* | |
| 2 | Inputs a date and time that conflicts with an existing event for "Date:", "Start Time:", and "End Time:" | |
| 3 | | Prompts "Time conflict with existing event. Please enter a different date and time." |
| 4 | | Returns to main menu |

---

## Go To Day
| Step | User | System |
|---|---|---|
| 1 | Inputs g for (G)o to day| |
| 2 | | Prompts and collects "Date:" |
| 3 | | Prints the day of the week, date, and any events from that day |

### Go To Day with Invalid Date
| Step | User | System |
|---|---|---|
| 1 | Performs *Go To Day* | |
| 2 | Inputs an invalid date for "Date:" | |
| 3 | | Prompts "Invalid date. Please enter a valid date (MM/DD/YYYY)" |
| 4 | | Returns to previous menu |

---

## Event List
| Step | User | System |
|---|---|---|
| 1 | Inputs e for (E)vent list| |
| 2 | | Prints 2 lists of events: One time events sorted by timestamp and reoccurring events sorted by starting date.

### Event List with No Events
| Step | User | System |
|---|---|---|
| 1 | Performs *Event List* | |
| 2 | | Prints "No events found." for both lists of events |

---

## Delete Event
| Step | User | System |
|---|---|---|
| 1 | Inputs d for (D)elete| |
| 2 | | Prompts and collects "(S)elected, (A)ll, (R)ecurring" |

### Delete Selected Event
| Step | User | System |
|---|---|---|
| 1 | Performs *Delete Event* | |
| 2 | Inputs s for (S)elected | | |
| 3 | | Prompts and collects "Name:" |
| 4 | | Deletes the event with the given name and returns to main menu |

#### Delete Selected Event with Invalid Name
| Step | User | System |
|---|---|---|
| 1 | Performs *Delete Selected Event* | |
| 2 | Enters an invalid name for "Name:" | |
| 3 | | Prompts "Event not found. Please enter a valid event name." |
| 4 | | Return to previous menu

### Delete All Events
| Step | User | System |
|---|---|---|
| 1 | Performs *Delete Event* | |
| 2 | Inputs a for (A)ll | | |
| 3 | | Deletes all events and returns to main menu |

### Delete Recurring Events
| Step | User | System |
|---|---|---|
| 1 | Performs *Delete Event* | |
| 2 | Inputs r for (R)ecurring | | |
| 3 | | Prompts and collects "Name:" |
| 4 | | Deletes all events with the given name and returns to main menu |

---

## Exit Program
| Step | User | System |
|---|---|---|
| 1 | Inputs q for (Q)uit| | |
| 2 | | Exits the program |