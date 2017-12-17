#!/usr/bin/env python3
# coding: utf-8

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
foldername = "shut_the_box_analysis.AnalysisWinningDepth/"
filenames1 = ["sumByLevel_concat",
              "sumByLevel_sum"]
filenames2 = ["winningLeafByLevel_concat",
              "winningLeafByLevel_sum"]


# ------------------------------------------------
# Plot function
# ------------------------------------------------
def plot(filename, y1, y2):
    input_file = root_foldername + foldername + filename + ".csv"
    output_file = root_foldername + foldername + filename

    sns.set(style="whitegrid", context="paper")

    fig, (ax1, ax2) = plt.subplots(2, 1, figsize=(8, 8))
    data = pd.read_csv(input_file)

    sns.barplot(x='Level', y=y1, data=data, ax=ax1).set_title('Sum of score by level')
    sns.barplot(x='Level', y=y2, data=data, ax=ax2).set_title('Mean of score by level')

    plt.savefig(output_file)


# ------------------------------------------------
# Script
# ------------------------------------------------
for f in filenames1:
    plot(f, 'Sum', 'Mean')

for f in filenames2:
    plot(f, 'Sum', 'Size')
