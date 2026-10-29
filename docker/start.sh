#!/bin/sh
set -e
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

docker-compose -f $SCRIPT_DIR/docker-compose.yml up -d

echo ""
echo "✅ Started!"
echo ""
echo "Available srvices:"
echo "  • PostgreSQL: localhost:5432"
echo "  • Redis:      localhost:6379"
echo "  • Kafka:      localhost:9092"
echo ""
