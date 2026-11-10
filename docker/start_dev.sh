#!/bin/sh
set -e
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

docker-compose -f $SCRIPT_DIR/docker-compose.yml -f $SCRIPT_DIR/docker-compose.dev-tools.yml up -d

echo ""
echo "✅ Started!"
echo ""
echo "Available services:"
echo "  • PostgreSQL: localhost:5432"
echo "  • Redis:      localhost:6379"
echo "  • Kafka:      localhost:9092"
echo ""
echo "Dev Tools:"
echo "  • Kafka UI:        http://localhost:8090"
echo "  • Redis Commander: http://localhost:8091"
echo "  • PGAdmin4: http://localhost:5050"
