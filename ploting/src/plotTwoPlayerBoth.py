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
file_max = root_foldername + foldername + "distribution_beat_max.csv"
file_min = root_foldername + foldername + "distribution_beat_min.csv"

# ------------------------------------------------
# Plot function
# ------------------------------------------------
def plot(filename, filename_min, width, height, label):
    output_file = filename[:-4]

    sns.set(style="whitegrid", context="paper")

    fig, (ax1) = plt.subplots(1, 1, figsize=(width, height))
    data = pd.read_csv(filename)
    data_min = pd.read_csv(filename_min)

    plt.xticks(rotation=label)
    sns.barplot(x='Score', y='Probability to beat player 2', data=data, ax=ax1, color="#3b5998").set_title('Distribution')
    sns.barplot(x='Score', y='Probability to beat player 2', data=data_min, ax=ax1, color="#8b9dc3").set_title('Distribution')
    plt.savefig(output_file + "_both")
    plt.close(fig)

# ------------------------------------------------
# Script
# ------------------------------------------------
plot(file_max, file_min, 8, 6, 0)
