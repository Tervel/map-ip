import subprocess
import csv

outlist = []

with open("blacklist.csv", "rb") as csvFile:
    reader = csv.reader(csvFile, delimiter="\t")
    count = enumerate(reader)
    for line in count:
        outlist.append(line[1][0])

with open("blacklistGeo.csv", "wb") as writeFile:
    writer = csv.writer(writeFile, delimiter=',')
    for line in outlist:
        output = subprocess.check_output(["curl", "ipinfo.io/{}/loc".format(line)])
        output = output[:-1]
        writer.writerow([line, output])




