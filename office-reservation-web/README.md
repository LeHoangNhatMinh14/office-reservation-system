# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react/README.md) uses [Babel](https://babeljs.io/) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh


Keywords
reservation system, schedule, work schedule, office, home, schedule management, team manager, reservation, table, notification

Summary
Discussion on office reservation system
- Discussed the need for an access control system using passwords.
- Expressed the desire for a social system to allow users to log in with their work accounts.
- Identified the need for a role management system with three different roles: admin, team manager, and user.

Overview of Team Management System
- Team managers can reserve tables/islands and manage team members.
- Admins can create rooms, and manage team members.
- Team managers can add or remove members from teams.

Scheduling
- Employees should be able to add their work schedules to the system, including specific work hours.
- Employees should be able to indicate their start and end times, which may vary.

Discussion about a reservation system
- Employees can view their schedules and reserve desks.

Room Management System
- Managers have access to their teams and can receive notifications for team-related activities.
- Users can view available rooms and make reservations, but cannot see who made the reservations.

Reservation and Scheduling System
- Reservation system should notify the team if a team reservation is canceled.
- Users should be able to schedule outside of the given schedule.
- If a table becomes available, the first person on the waiting list should be notified.

Discussion on Team Management Features
- Teams can only be managed by their respective managers.
- Team members can view their teams and add members to their own teams.

Discussion about work schedule and communication
- Discussed the hybrid work schedule, which includes working from home and in the office.

Discussion about scheduling a workplace and prioritizing functionalities
- Scheduling a workplace for next week, but the desired table is reserved.
- Will be put on the waiting list and get a reservation if the current one is canceled.

Administering a Minimal Viable Product
- Administering a minimal viable product with basic functionalities.
- Prioritizing tasks and features for efficient development.

Discussion on Table Reservation System
- Reservation time slots are set for every 30 minutes, from 7am to 7pm.
- Each group will receive similar constraints and schedules.

Work Schedule
- Different work schedules for office and desk employees.
- Option to choose between working 4 days in office and 1 day from home in even weeks.
- In odd weeks, employees can work 3 days in office and 2 days from home.

Discussion on Schedule Management
- Schedules can be viewed but not edited by others.
- Team managers can edit reservations but not individual schedules.

Managing Team Reservations
- Team managers should reserve rooms for their team members.
- Team members can request leave dates themselves.
- Company colors are red and white.

Reservation for a Table
- The preference is for a table for one person.
- The language used for the reservation is English.

Reservation System for Islands
- The Islands can be reserved in their entirety.
- Reservations can be made for days or months in advance.
- Recurring reservations can be set up for specific times and dates.

Data Request for User Creation
- Admin needs to provide basic information like name, email, and password to create a user.
- Confirmation on whether the user can be deleted or not.

Discussion on User's Role in Team
- Discussed the role of a user in a team and their access to information.
- A team without a team manager is not possible, so the user needs to be assigned a team manager.

Discussion on deleting user account
- When a user account is deleted, all associated reservations and schedules should also be deleted.
- However, the system needs to keep a record of the deletion for audit purposes.

Discussion on Team and Key Manager Limits
- Discussed the limit of a team being 10 and whether there is a limit on the number of team managers, which is technically ten.
- Expressed the desire for flexibility in team size and the ability to change team size in the future.

Reservation Release and Notification
- If a user no longer exists, future reservations should be released.
- Team reservations should not be affected by user departures.

- Bus reservations only exist in data, and cannot be shot on future reservations.
- Team reservations are team-less, and individual reservations can also be made.
- Islands are automatically team-only, while islands can be reserved as a single table or for a team.

Overview of Team Information
- Seeking an overview of team members, their schedules, and personal schedule.

Overview of Office Schedule and Reservations
... (27 lines left)