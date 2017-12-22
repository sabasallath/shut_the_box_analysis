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
foldername = "shut_the_box_analysis.analysis.AnalysisDistribution/"
filenames1 = [f for f in os.listdir(root_foldername + foldername)
              if f.startswith("distribution_sum") and f.endswith(".csv")]
filenames2 = [f for f in os.listdir(root_foldername + foldername)
              if f.startswith("distribution_win_loose") and f.endswith(".csv")]
filenames3 = [f for f in os.listdir(root_foldername + foldername)
              if f.startswith("distribution_concat") and f.endswith(".csv")]
filenames4 = [f for f in os.listdir(root_foldername + foldername)
              if f.startswith("distribution_reach_one") and f.endswith(".csv")]

# ------------------------------------------------
# Plot function
# ------------------------------------------------
def plot(filename, width, height, label):
    input_file = root_foldername + foldername + filename
    output_file = input_file[:-4]

    sns.set(style="whitegrid", context="paper")

    fig, (ax1) = plt.subplots(1, 1, figsize=(width, height))
    data = pd.read_csv(input_file)

    plt.xticks(rotation=label)
    sns.barplot(x='Sum', y='Probability', data=data, ax=ax1).set_title('Probability by Sum Theorical')
    plt.savefig(output_file)
    plt.close(fig)

# ------------------------------------------------
# Script
# ------------------------------------------------
for f in filenames1:
    plot(f, 8, 6, 0)
for f in filenames2:
    plot(f, 6, 8, 0)
for f in filenames3:
    plot(f, 60, 8, -90)
for f in filenames4:
    plot(f, 8, 6, 0)