from google.appengine.ext import ndb
import webapp2
import json
import time
import datetime
from datetime import datetime
from datetime import time

###############################
## Clinic Handlers and class
###############################

class Clinic(ndb.Model):
	name = ndb.StringProperty(required=True)
	openHour = ndb.IntegerProperty()
	openMinute = ndb.IntegerProperty()
	closeHour = ndb.IntegerProperty()
	closeMinute = ndb.IntegerProperty()
	lat = ndb.FloatProperty()
	lon = ndb.FloatProperty()
	procedures = ndb.StringProperty(repeated=True)

class ClinicHandler(webapp2.RequestHandler):
	def post(self):
		clinic_data = json.loads(self.request.body)
		new_clinic = Clinic(name=clinic_data['name'])
		new_clinic.put()
		if 'openHour' in clinic_data:
			new_clinic.openHour = clinic_data['openHour']
			new_clinic.put()
		if 'openMinute' in clinic_data:
			new_clinic.openMinute = clinic_data['openMinute']
			new_clinic.put()
		if 'closeHour' in clinic_data:
			new_clinic.closeHour = clinic_data['closeHour']
			new_clinic.put()
		if 'closeMinute' in clinic_data:
			new_clinic.closeMinute = clinic_data['closeMinute']
			new_clinic.put()			
		if 'lat' in clinic_data:
			new_clinic.lat = clinic_data['lat']
			new_clinic.put()
		if 'lon' in clinic_data:
			new_clinic.lon = clinic_data['lon']
			new_clinic.put()		
		clinic_dict = new_clinic.to_dict()
		identifier = new_clinic.key.urlsafe()
		clinic_dict['id'] = identifier
		clinic_dict['self'] = '/clinic/' + identifier
		self.response.write(json.dumps(clinic_dict))

	def put(self, id=None):
		request_data = json.loads(self.request.body)
		action = request_data['action']
		if action == 'add':
			request_clinic = request_data['clinic']
			request_procedure = request_data['procedure']
			clinic_key = ndb.Key(urlsafe=request_clinic)
			clinic_entity = clinic_key.get()
			current_procedures = clinic_entity.procedures
			self.response.write(current_procedures)
			current_procedures.append(request_procedure)
			self.response.write(current_procedures)
			clinic_entity.put()
			self.response.write(clinic_entity)
		if action == 'remove':
			request_clinic = request_data['clinic']
			request_procedure = request_data['procedure']
			clinic_key = ndb.Key(urlsafe=request_clinic)
			clinic_entity = clinic_key.get()
			current_procedures = clinic_entity.procedures
			new_procedures = []
			for inspected in current_procedures:
				if inspected != request_procedure:
					new_procedures.append(inspected)
			clinic_entity.procedures = new_procedures
			clinic_entity.put()
			self.response.write(current_procedures)
			self.response.write(new_procedures)
			self.response.write(clinic_entity)
			
		
	def get(self, id=None):
		if id:
			c = ndb.Key(urlsafe=id).get()
			c_d = c.to_dict()
			c_d['self'] = "/clinic/" + id
			self.response.write(json.dumps(c_d))
			
	def delete(self, id=None):
		c = ndb.Key(urlsafe=id).get()
		c.key.delete()

#	def patch(self, id=None):
#                self.response.write("in patch with id none")
#                e = ndb.Key(urlsafe=id).get()
#		# self.response.write(e)
#		new_data = json.loads(self.request.body)
#		for a in new_data:
#			if a == 'name':
#				e.name = new_data['name']
#				e.put()
#			if a == 'openTime':
#				e.openTime = new_data['openTime']
#				e.put()
#			if a == 'closeTime':
#				e.openTime = new_data['closeTime']
#				e.put()
#			if a == 'loc':
#				e.loc = new_data['loc']
#				e.put()
#		e_dict = e.to_dict()
#		self.response.write(json.dumps(e_dict))

	def patch(self, id=None):
                self.response.write("in patch without id none")
		clinic_data = json.loads(self.request.body)
		mod_request = clinic_data['id']
		new_clinic = ndb.Key(urlsafe=mod_request).get()
		if 'name' in clinic_data:
			new_clinic.name = clinic_data['name']
			new_clinic.put()
		if 'openHour' in clinic_data:
			new_clinic.openHour = clinic_data['openHour']
			new_clinic.put()
		if 'openMinute' in clinic_data:
			new_clinic.openMinute = clinic_data['openMinute']
			new_clinic.put()
		if 'closeHour' in clinic_data:
			new_clinic.closeHour = clinic_data['closeHour']
			new_clinic.put()
		if 'closeMinute' in clinic_data:
			new_clinic.closeMinute = clinic_data['closeMinute']
			new_clinic.put()			
		if 'lat' in clinic_data:
			new_clinic.lat = clinic_data['lat']
			new_clinic.put()
		if 'lon' in clinic_data:
			new_clinic.lon = clinic_data['lon']
			new_clinic.put()		
		clinic_dict = new_clinic.to_dict()
		self.response.write(json.dumps(clinic_dict))		
		
###############################
## Procedure Handlers and class
###############################
		
class Procedure(ndb.Model):
	name = ndb.StringProperty()
	duration = ndb.FloatProperty()
	cost = ndb.FloatProperty()
	operator = ndb.StringProperty()

class ProcedureHandler(webapp2.RequestHandler):
	def post(self):
		proc_data = json.loads(self.request.body)
		new_proc = Procedure(name=proc_data['name'])
		new_proc.put()
		if 'duration' in proc_data:
			new_proc.duration = proc_data['duration']
			new_proc.put()
		if 'cost' in proc_data:
			new_proc.cost = proc_data['cost']
			new_proc.put()
		if 'operator' in proc_data:
			new_proc.operator = proc_data['operator']
			new_proc.put()		
		proc_dict = new_proc.to_dict()
		identifier = new_proc.key.urlsafe()
		proc_dict['id'] = identifier
		proc_dict['self'] = '/procedure/' + identifier
		self.response.write(json.dumps(proc_dict))

	def get(self, id=None):
		if id:
			p = ndb.Key(urlsafe=id).get()
			p_d = p.to_dict()
			p_d['self'] = "/procedure/" + id
			self.response.write(json.dumps(p_d))

	def delete(self, id=None):

                self.response.write("in delete procedure")

                # get the entity to delete
		p = ndb.Key(urlsafe=id).get()
#               self.response.write(p)

		# get the dictionary of this entity
#		p_d = p.to_dict()
#                self.response.write(p_d)

		# get the string for the procedure's id
#		proc_string = p_d['name']
#               self.response.write(proc_string)
#                self.response.write(id)
                proc_string = id
#                proc_query = Procedure.query()

		# get all the clinics
		clinic_query = Clinic.query()

		# search all clinics for the procedure
		for clinic_entity in clinic_query:
			clinic_dict = clinic_entity.to_dict()
			clinic_procs = clinic_dict['procedures']

			# loop through clinic's procedures to remove 
			# the procedure being deleted
			new_procedures = []
			for curr_proc in clinic_procs:
				if curr_proc != proc_string:
					new_procedures.append(curr_proc)

			# update clinic_entity
			clinic_entity.procedures = new_procedures
			clinic_entity.put()	

		# delete the procedure after removing it 
		# from all the clinics
		p.key.delete()		
		
	def patch(self, id=None):
                # self.response.write("in procedure patch")
		new_data = json.loads(self.request.body)
		mod_request = new_data['id']
		e = ndb.Key(urlsafe=mod_request).get()
		# e = ndb.Key(urlsafe=id).get()
		# self.response.write(e)
#		new_data = json.loads(self.request.body)
#		self.response.write(new_data)
		for a in new_data:
			if a == 'name':
	                        #self.response.write("* setting name *")
                                e.name = new_data['name']
				e.put()
			if a == 'duration':
                                #self.response.write("* setting duration *")
				e.duration = new_data['duration']
				e.put()
			if a == 'cost':
                                #self.response.write("* setting cost *")
				e.cost = new_data['cost']
				e.put()
			if a == 'operator':
                                #self.response.write("* setting operator *")
				e.operator = new_data['operator']
				e.put()
		e_dict = e.to_dict()
		self.response.write(json.dumps(e_dict))
		
###############################
## AllClinics Handler and class
###############################		
		
class AllClinicsHandler(webapp2.RequestHandler):

	def get(self, id=None):
		query = Clinic.query()
		return_object = []
		for clinic_entity in query:
			clinic_dict = clinic_entity.to_dict()
			identifier = clinic_entity.key.urlsafe()
			clinic_dict['id'] = identifier
			return_object.append(clinic_dict)
		self.response.write(json.dumps(return_object))
			
###############################
## AllProcedures Handler and class
###############################		
		
class AllProceduresHandler(webapp2.RequestHandler):

	def get(self, id=None):
		query = Procedure.query()
		return_object = []
		for proc_entity in query:
			proc_dict = proc_entity.to_dict()
			identifier = proc_entity.key.urlsafe()
			proc_dict['id'] = identifier
			return_object.append(proc_dict)
		self.response.write(json.dumps(return_object))

###############################
## Main Page Handler and class
###############################	
			
class MainPage(webapp2.RequestHandler):

    def get(self):
		self.response.write("Hello.")
		self.response.write("\n")
		self.response.write("No action performed by the main page handler.")

allowed_methods = webapp2.WSGIApplication.allowed_methods
new_allowed_methods = allowed_methods.union(('PATCH',))
webapp2.WSGIApplication.allowed_methods = new_allowed_methods
		
app = webapp2.WSGIApplication([
    ('/', MainPage),
	('/clinic', ClinicHandler),
	('/clinic/(.*)', ClinicHandler),
	('/procedure', ProcedureHandler),
	('/procedure/(.*)', ProcedureHandler),
	('/clinics', AllClinicsHandler),
	('/procedures', AllProceduresHandler)
], debug=True)