# Office Reservation System (Semester 3 Project)

[![Java](https://img.shields.io/badge/Java-17-blue)]()
[![React](https://img.shields.io/badge/React-18-blue)]()
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1-green)]()

> A full-stack office reservation system developed for Driessen to optimize workspace allocation.

## Overview
This project is designed to streamline office workspace management, allowing employees to easily reserve desks and view availability.

- **Team**: 4-person agile team
- **Duration**: 6-month project
- **Key Achievement**: Since the team lost a member we were just able to get the MVP finished in time

## Key Features
- Schedule workspaces with drag-and-drop functionality
- View workspace availability in real-time
- Secure login via JWT authentication
- Integrated with external calendar API for real-time updates

## 🛠 My Responsibilities

### Project Leadership
- Led the team as a **second Scrum Master** in an agile environment, facilitating daily standups and sprint planning.
- Acted as a **second Project Owner**, helping to prioritize and clarify project requirements.
- Coordinated communication between the team and stakeholders, ensuring the project stayed on track and aligned with project goals.
- Onboarded a new developer to replace the original backend member, helping them get up to speed quickly.

### Technical Contributions
**Frontend** (React):
- Developed the scheduling interface with drag-and-drop functionality, allowing users to easily book workspaces.
- Implemented JWT authentication for secure user login.

**Backend** (Spring Boot):
- Designed the MySQL database schema to manage reservations and user data.
- Optimized the application setup process by containerizing the application with Docker, reducing setup time.

**Some of my front-end work**
Team management system
![Team management](https://imgur.com/do6AUQL.png)

Scheduling system
![Scheduling Interface](https://imgur.com/SVG3e4h.png)

## Architecture
```mermaid
graph TD
    A[Frontend: React] --> B[Spring Boot API]
    B --> C[MySQL Database]
