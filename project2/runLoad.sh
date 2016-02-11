#!/bin/bash

# Drop tables if they exist
mysql CS144 < drop.sql

# Create relevant tables
mysql CS144 < create.sql

# Compile and run the parser
ant
ant run-all

# Duplicate removal
sort -u item.csv > item.csv.tmp
sort -u seller.csv > seller.csv.tmp
sort -u bidder.csv > bidder.csv.tmp
sort -u bid.csv > bid.csv.tmp
sort -u category.csv > category.csv.tmp

# Load csv files
mysql CS144 < load.sql

rm *.csv*
rm -rf bin
