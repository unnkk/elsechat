#!/bin/sh

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

docker-compose -f $SCRIPT_DIR/docker-compose.yml -f $SCRIPT_DIR/docker-compose.dev-tools.yml down
docker-compose up -d

echo ""
echo "✅ Restarted!"
