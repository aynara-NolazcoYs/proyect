#!/bin/bash

# Start SQL Server container for EduFuturo
docker-compose up -d

echo "================================"
echo "SQL Server started successfully!"
echo "================================"
echo ""
echo "Connection Details:"
echo "- Host: localhost:1433"
echo "- Database: EduFuturoDB"
echo "- Username: sa"
echo "- Password: EduFuturo@2024"
echo ""
echo "Now you can run: mvn spring-boot:run"
