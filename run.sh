#!/usr/bin/env bash

# ----------- UTILS -----------
function safe_error_handling {
    set -euo pipefail
    IFS=$'\n\t'
}
function safe_location {
    SCRIPT=$(realpath $0)
    SCRIPT_PATH=$(dirname ${SCRIPT})
    cd ${SCRIPT_PATH}
}
#safe_error_handling
safe_location
# ----------------------------
if [ ! -d "ploting/env" ]; then
    ploting/setenv.sh
fi
source ploting/env/bin/activate

ploting/src/plot.py
ploting/src/plotDistribution.py
ploting/src/plotDistributionSimulate.py
ploting/src/plotTwoPlayer.py
