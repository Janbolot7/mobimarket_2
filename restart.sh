#!/bin/bash

# Stop the running process
sh shutdown.sh

# Wait for a few seconds to allow the process to stop
sleep 5

# Start the application again
sh startup.sh