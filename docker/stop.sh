#!/bin/sh
set -e
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

docker-compose -f $SCRIPT_DIR/docker-compose.yml -f $SCRIPT_DIR/docker-compose.dev-tools.yml down

echo ""
echo "✅ Containers stopped!"
