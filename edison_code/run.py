import os
import re
import sys
import json
import time
import struct
import random
import logging
import requests
import argparse
import pprint
import requests
import datetime
import time
from pgoapi import PGoApi
from pgoapi.utilities import f2i, h2f
from pgoapi import utilities as util
from google.protobuf.internal import encoder
from geopy.geocoders import GoogleV3
from s2sphere import Cell, CellId, LatLng

# Setting up some logging up in this bish
log = logging.getLogger(__name__)


# Let's get some important things from the environment

PTC_USERNAME = os.environ.get('PTC_USERNAME')
PTC_PASSWORD = os.environ.get('PTC_PASSWORD')
LOCATION = '3708 S Las Vegas Blvd, Las Vegas, NV 89109'
INGEST_URL = 'https://apm-timeseries-services-hackapm.run.aws-usw02-pr.ice.predix.io/v2/time_series'
TENANT_ID = '8B8039C92C3E4EDFAB97CE576492D70C'
headers = {
    'authorization': "bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIxYjVmYjY1MC0wZjI3LTRmMzktYTI5OC1iOTlhZTZmZjk1MjUiLCJzdWIiOiIwYWVjYmRiZi1jOGMzLTRiNjItYmM5Yy01ZjE2ZDhlYzNkZTciLCJzY29wZSI6WyJwYXNzd29yZC53cml0ZSIsIm9wZW5pZCJdLCJjbGllbnRfaWQiOiJpbmdlc3Rvci45Y2YzM2NlMzdiZjY0YzU2ODFiNTE1YTZmNmFhZGY0NyIsImNpZCI6ImluZ2VzdG9yLjljZjMzY2UzN2JmNjRjNTY4MWI1MTVhNmY2YWFkZjQ3IiwiYXpwIjoiaW5nZXN0b3IuOWNmMzNjZTM3YmY2NGM1NjgxYjUxNWE2ZjZhYWRmNDciLCJncmFudF90eXBlIjoicGFzc3dvcmQiLCJ1c2VyX2lkIjoiMGFlY2JkYmYtYzhjMy00YjYyLWJjOWMtNWYxNmQ4ZWMzZGU3Iiwib3JpZ2luIjoidWFhIiwidXNlcl9uYW1lIjoicG9rZW1vbiIsImVtYWlsIjoiZGF2aWQuc3RlaW5iZXJnZXJAZ2UuY29tIiwiYXV0aF90aW1lIjoxNDY5NDIzNTIzLCJyZXZfc2lnIjoiYmRiZTQ5YWYiLCJpYXQiOjE0Njk0MjM1MjMsImV4cCI6MTQ2OTUwOTkyMywiaXNzIjoiaHR0cHM6Ly9kOWVmMTA2Yy03MDQ4LTQ4NmUtYTc5Zi05YzgwODI3YjhhMTQucHJlZGl4LXVhYS5ydW4uYXdzLXVzdzAyLXByLmljZS5wcmVkaXguaW8vb2F1dGgvdG9rZW4iLCJ6aWQiOiJkOWVmMTA2Yy03MDQ4LTQ4NmUtYTc5Zi05YzgwODI3YjhhMTQiLCJhdWQiOlsiaW5nZXN0b3IuOWNmMzNjZTM3YmY2NGM1NjgxYjUxNWE2ZjZhYWRmNDciLCJwYXNzd29yZCIsIm9wZW5pZCJdfQ.lFD6bHFJXuZH2Awxatp1MvHVDUHNTUpTckrXNASWeYfz6GV5DDWe9WIGYzTyTrN5UiaFi95MwzVrNR9OGh59VAQ-vvBpo5G7WlG8HILJHhBPgMSXSYtNf4qgMzwHPay5RqVmurZVZ8D4xiy9ZBWOSCZqnOtIEt25VA_hMhI8wPvXGHkU79YqbpLpyco6WACQ-IxrfVRB1EJvZnoZz4JIG84iDrfbt4pVPhbSYvrJ_kzyRX71NcYhNcED4dNYEftCQxxUBHkOcuXxcpAvJWJSEeaQAjELQ6LU7hMO5HmPc0Ztip2yl-2n0etL-0V73ly0udAZdmqb4Wm65QvsBHlDmg",
    'tenant': "8B8039C92C3E4EDFAB97CE576492D70C",
    'content-type': "application/json",
    'accept': "application/json",
    'cache-control': "no-cache",
    }



def post_data(payload):
    r = requests.post(INGEST_URL, headers=headers, data=payload)
    bob = r.status_code
    print bob
 

def get_pos_by_name(location_name):
    geolocator = GoogleV3()
    loc = geolocator.geocode(location_name)
    if not loc:
        return None

    log.info('Your given location: %s', loc.address.encode('utf-8'))
    log.info('lat/long/alt: %s %s %s', loc.latitude, loc.longitude, loc.altitude)

    return (loc.latitude, loc.longitude, loc.altitude)

def get_cell_ids(lat, long, radius = 10):
    origin = CellId.from_lat_lng(LatLng.from_degrees(lat, long)).parent(15)
    walk = [origin.id()]
    right = origin.next()
    left = origin.prev()

    # Search around provided radius
    for i in range(radius):
        walk.append(right.id())
        walk.append(left.id())
        right = right.next()
        left = left.prev()

    # Return everything
    return sorted(walk)

def encode(cellid):
    output = []
    encoder._VarintEncoder()(output.append, cellid)
    return ''.join(output)

def main():
        
    # instantiate pgoapi
    api = PGoApi()
    position = get_pos_by_name(LOCATION)
    # provide player position on the earth
    api.set_position(*position)

    if not api.login('ptc', PTC_USERNAME, PTC_PASSWORD):
        return
    # execute the RPC call
    response_dict = api.call()

    pokemon_json = find_poi(api, position[0], position[1])

def find_poi(api, lat, lng):
    poi = {'pokemons': {}, 'forts': []}
    step_size = 0.0005
    step_limit = 65
    coords = generate_spiral(lat, lng, step_size, step_limit)
    for coord in coords:
        lat = coord['lat']
        lng = coord['lng']
        api.set_position(lat, lng, 0)     
        #get_cellid was buggy -> replaced through get_cell_ids from pokecli
        #timestamp gets computed a different way:
        cell_ids = get_cell_ids(lat, lng)
        timestamps = [0,] * len(cell_ids)
        api.get_map_objects(latitude = util.f2i(lat), longitude = util.f2i(lng), since_timestamp_ms = timestamps, cell_id = cell_ids)
        response_dict = api.call()
        if 'status' in response_dict['responses']['GET_MAP_OBJECTS']:
            if response_dict['responses']['GET_MAP_OBJECTS']['status'] == 1:
                for map_cell in response_dict['responses']['GET_MAP_OBJECTS']['map_cells']:
                    if 'wild_pokemons' in map_cell:
                        for pokemon in map_cell['wild_pokemons']:
                            pokekey = get_key_from_pokemon(pokemon)
                            long_id = pokemon['longitude']
                            lat_id = pokemon['latitude']
                            poke_id = pokemon['pokemon_data']['pokemon_id']
                            time_now = str(time.strftime('%Y-%m-%dT%H:%M:%S.')) + str(int(round(time.time() * 1000)))[-3:]
                            payload = "{\n  \"tags\": [\n    {\n      \"tagId\": \"POKE5-CASINO-COSMOPOLITAN-PM_UNIT-LONGITUDE\",\n      \"errorCode\": null,\n      \"errorMessage\": null,\n      \"data\": [ \n          {\"ts\": \""+time_now+"\", \"q\": \"3\", \"v\":\""+str(long_id)+"\"}]\n    }\n  ]\n}"
                            payload2 = "{\n  \"tags\": [\n    {\n      \"tagId\": \"POKE5-CASINO-COSMOPOLITAN-PM_UNIT-LATITUDE\",\n      \"errorCode\": null,\n      \"errorMessage\": null,\n      \"data\": [ \n          {\"ts\": \""+time_now+"\", \"q\": \"3\", \"v\":\""+str(lat_id)+"\"}]\n    }\n  ]\n}"
                            payload3 = "{\n  \"tags\": [\n    {\n      \"tagId\": \"POKE5-CASINO-COSMOPOLITAN-PM_UNIT-ID\",\n      \"errorCode\": null,\n      \"errorMessage\": null,\n      \"data\": [ \n          {\"ts\": \""+time_now+"\", \"q\": \"3\", \"v\":\""+str(poke_id)+"\"}]\n    }\n  ]\n}"
                            send_long = post_data(payload)
                            send_lat = post_data(payload2)
                            send_pokeid = post_data(payload3)

                            print pokemon

def get_key_from_pokemon(pokemon):
    return '{}-{}'.format(pokemon['spawnpoint_id'], pokemon['pokemon_data']['pokemon_id'])

def print_gmaps_dbug(coords):
    url_string = 'http://maps.googleapis.com/maps/api/staticmap?size=400x400&path='
    for coord in coords:
        url_string += '{},{}|'.format(coord['lat'], coord['lng'])
    print(url_string[:-1])

def generate_spiral(starting_lat, starting_lng, step_size, step_limit):
    coords = [{'lat': starting_lat, 'lng': starting_lng}]
    steps,x,y,d,m = 1, 1, 0, 1, 1
    rlow = 0.0
    rhigh = 0.00005

    while steps < step_limit:
        while 2 * x * d < m and steps < step_limit:
            x = x + d
            steps += 1
            lat = x * step_size + starting_lat + random.uniform(rlow, rhigh)
            lng = y * step_size + starting_lng + random.uniform(rlow, rhigh)
            coords.append({'lat': lat, 'lng': lng})
        while 2 * y * d < m and steps < step_limit:
            y = y + d
            steps += 1
            lat = x * step_size + starting_lat + random.uniform(rlow, rhigh)
            lng = y * step_size + starting_lng + random.uniform(rlow, rhigh)
            coords.append({'lat': lat, 'lng': lng})

        d = -1 * d
        m = m + 1
    return coords

if __name__ == '__main__':
    main()
