# e-Petiții 

## Backend API Documentation
Welcome to the E Petitii Backend API documentation. 
This guide outlines the various endpoints and their functionalities within the e-Petiții system, a platform designed for managing and interacting with various types of petitions directed on the implication
of the diaspora in country's decisional processes.


## API Endpoints
### Categories List
- **Endpoint:** /api/categories
- **Method:** GET
  
**Description:** Fetches a list of petition categories.

**Response:**  JSON array of categories with "id", "name", and "i18n" for internationalization.


### Regions List

- **Endpoint:** /api/regions

- **Method:** GET
  
**Description:** Retrieves a list of regions.

**Response:**  JSON array of regions, each with an "id" and "name".

### Receivers List
- **Endpoint:** /api/receivers
- **Method:** GET

**Description:** Provides a list of potential receivers for petitions.

**Response:** JSON array of receivers with "id", "name", and "i18n".

### Petitions List
- **Endpoint:** /api/petitions
- **Method:** GET

**Description:** Retrieves a list of petitions, with optional filtering.

**Parameters:** categories, status, region, search, sort

**Response:** JSON array of petition details.

### Single Petition by ID

- **Endpoint:** /api/petitions/{petition_id}
- **Method:** GET

**Description:** Gets details of a single petition by its ID.

**Response:** Detailed JSON object of the petition.

### Create Petition

**Endpoint:** /api/petitions/create
- **Method:** POST

**Description:** Allows for the creation of a new petition.

**Payload:** JSON with petition details like "initiator_idnp", "name", etc.

**Response:** JSON with "petition_id".

### Sign Petition 
- **Endpoint:** /api/petitions/{petition_id}/sign
- **Method:** POST

**Description:** Enables users to sign a specific petition.
**Payload:** JSON with "user_idnp".
**Response:** JSON with a confirmation message.

### Email Notifications Subscription
- **Endpoint:** /api/emails/subscribe
- **Method:** POST

**Description:** Subscribes
