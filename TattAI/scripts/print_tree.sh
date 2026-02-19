#!/usr/bin/env sh
find . -maxdepth 4 -type f | sed 's|^\./||'
