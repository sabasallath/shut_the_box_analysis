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
filenames = [f for f in os.listdir(root_foldername + foldername)
             if f.startswith("distribution_sum") and f.endswith(".csv")]


# ------------------------------------------------
# Plot function
# ------------------------------------------------
def plot(filename):
    input_file = root_foldername + foldername + filename
    output_file = input_file[:-4]

    sns.set(style="whitegrid", context="paper")
    fig, (ax1) = plt.subplots(1, 1, figsize=(8, 4))
    data = pd.read_csv(input_file)

    sns.barplot(x='Sum', y='Probability', data=data, ax=ax1).set_title('Probability by Sum')

    plt.savefig(output_file)


# ------------------------------------------------
# Script
# ------------------------------------------------
for f in filenames:
    plot(f)
