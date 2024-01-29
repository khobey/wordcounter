# Building and the application
Docker build and run 

```
docker build -t project/wordcounter:1.0 .
docker run -p 8080:8080 project/wordcounter:1.0
```
# Getting K Most Frequent Words
Upload a text file

```
POST http://localhost:8080/api/getKMostFrequentWords
Header: Content-type multipart/form-data
Body: file = <filename>
	   K = <integer>
```
where
* file - is the input file where the most frequent words are computed. Required
* K -  number of top K most frequent words. Required

A successful response will look like

```
200 OK
{
	results : [
		"word1" : 1
		]
}
```

Other responses can be:

400 - Bad request when inputs are not valid or missing

413 - File uploaded is too big. 2MB is the limit

500 - Server error when errors occur during processing

# Current Limitations
- File limit is up to 2MB
- File must be of .txt extension
- Local caching is available for the same text files uploaded to skip recalculation.

#Suggested improvements
- Validation of text contents
- Authentication and authorization (not implemented yet)
- Remote caching
- Limit can be configurable or based on available memory
