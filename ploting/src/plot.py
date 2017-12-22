#!/usr/bin/env python3
# coding: utf-8
import re

import os
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

# ------------------------------------------------
# Set current working directory to script location
# ------------------------------------------------
abspath = os.path.abspath(__file__)
dname = os.path.dirname(abspath)
os.chdir(dname)

# ------------------------------------------------
# File Location
# ------------------------------------------------
root_foldername = "../../output/"
foldername = "shut_the_box_analysis.analysis.AnalysisWinningDepth/"
filenames1 = [f for f in os.listdir(root_foldername + foldername)
              if f.startswith("sumByLevel") and f.endswith(".csv")]
filenames2 = [f for f in os.listdir(root_foldername + foldername)
              if f.startswith("winningLeafByLevel") and f.endswith(".csv")]

# ------------------------------------------------
# Plot function
# ------------------------------------------------
def plot(filename, y1, y2):
    input_file = root_foldername + foldername + filename
    output_file = input_file[:-4]

    sns.set(style="whitegrid", context="paper")

    fig, (ax1, ax2) = plt.subplots(2, 1, figsize=(8, 8))
    data = pd.read_csv(input_file)

    sns.barplot(x='Level', y=y1, data=data, ax=ax1).set_title('Sum of score by level')
    sns.barplot(x='Level', y=y2, data=data, ax=ax2).set_title('Mean of score by level')

    plt.savefig(output_file)
    plt.close(fig)

# ------------------------------------------------
# Script
# ------------------------------------------------
for f in filenames1:
    plot(f, 'Sum', 'Mean')

for f in filenames2:
    plot(f, 'Sum', 'Size')
