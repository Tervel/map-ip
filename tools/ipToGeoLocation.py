import requests
import json
import csv


def read_file(file):
    ip_addresses = []
    with open(file, 'r') as csvFile:
        reader = csv.reader(csvFile, delimiter='\t')
        count = enumerate(reader)
        for line in count:
            ip_addresses.append(line[1][0])

    return ip_addresses

def get_details(ip_addresses):
    ip_address_details = []

    for address in ip_addresses:
        request = requests.get('http://ip-api.com/json/{}'.format(address))
        ip_address_details.append(request.json())

    return ip_address_details

def main():
    ip_addresses = read_file('blacklist.csv')
    ip_addresses_json = get_details(ip_addresses)

    with open('blacklist.json', 'w') as outfile:
        json.dump(ip_addresses_json, outfile, sort_keys = True, indent = 4)

if __name__ == "__main__":
    main()

