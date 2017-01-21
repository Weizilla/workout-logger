from pymongo import MongoClient


class MongoDbLibrary(object):
    def __init__(self):
        self.client = None

    def connect_to_mongodb(self, host):
        self.client = MongoClient(host)

    def drop_mongodb_database(self, database):
        self.client.drop_database(database)

    def unset_garmin_ids(self, database):
        db = self.client[database]
        db.workout.update({}, {"$unset": {"garminIds": ""}}, multi=True)
