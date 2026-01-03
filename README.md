# Personalized-wellness-project
Personalized wellness and mindset analytics system built in Java with MySQL integration, focused on emotional assessment, progress tracking, and adaptive guidance.

1. User Management

Each user is identified by their name.

On launch, the system checks the database:

If the user exists → loads their history.

If not → creates a new user profile.

This enables true personalization instead of one-time usage.

2. Mindset Assessment Engine

The system includes a 15-question psychological self-assessment, covering:

motivation

energy

emotional regulation

confidence

focus

stress levels

social connection

self-talk

Each question provides four options (A–D), and the combined answers are evaluated using well-defined rules to determine the user’s current mindset, such as:

Energized and Positive

Growth-Oriented Mindset

Overwhelmed and Fatigued

Burnout Warning

Low Self-Worth

Emotionally Composed

Social Withdrawal and others

3. Mindset Scoring System

Each detected mindset is mapped to a numerical wellness score using a TreeMap-based scoring model:

Lower scores indicate healthier emotional states

Higher scores indicate increasing emotional strain or burnout risk

This numeric abstraction allows meaningful trend analysis over time.

4. Database Integration & Persistence

All data is stored in a local MySQL database using JDBC:

User profiles

Every assessment attempt

Detected mindset

Associated emotional score

Timestamped history

This transforms the application from a static program into a persistent system capable of long-term analysis.

5. Progress Tracking & Trend Analysis

The system continuously analyzes the user's emotional progression:

Retrieves the most recent past assessment

Compares previous and current scores

Detects:

recovery trends

stress increase

emotional stability

Generates context-aware feedback such as:

improvement encouragement

burnout warnings

stability reinforcement

Additionally, the system provides Mood Trend Analytics:

Displays the last 10 assessments in a formatted timeline

Identifies the most frequent emotional state

Calculates average emotional load

Produces intelligent guidance based on historical patterns

6. Personalized Support & Recommendations

Based on the user’s current mindset and progress:

Emotional support suggestions are provided (stress, fatigue, confusion, low mood, etc.)

Book recommendations are personalized using:

book similarity matching

genre-based emotional matching

mindset-based personalization

This makes the system adaptive rather than generic.

Technical Stack

Language: Java

Database: MySQL

Connectivity: JDBC

Architecture: Modular, service-oriented

Design Focus: Personalization, analytics, maintainability
