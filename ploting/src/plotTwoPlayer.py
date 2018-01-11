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
foldername = "shut_the_box_analysis.analysis.AnalysisTwoPlayer/"
filenames = [f for f in os.listdir(root_foldername + foldername)
              if f.startswith("distribution_beat") and f.endswith(".csv")]

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
    sns.barplot(x='Score', y='Probability to beat player 2', data=data, ax=ax1).set_title('Distribution')
    plt.savefig(output_file)
    plt.close(fig)

# ------------------------------------------------
# Script
# ------------------------------------------------
for f in filenames:
    plot(f, 8, 6, 0)
