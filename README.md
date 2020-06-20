# EZPISMSAPI

Send verification,promotional and transactional sms via this API.

## Getting Started

This library is super easy to implement and execute. Send any type of sms to users via this library.

### Prerequisites
You will need an EZIP SMS account . Create an account and get the AUTHENTICATION KEY .
Click in this page to redirect you to the website - http://bulksms.eduezip.com/login.html .
Copy and keep the authentication key .

### Implementation

Add it in your root build.gradle at the end of repositories:


	allprojects {
		repositories {

		maven { url 'https://jitpack.io' }
	}

 Add the dependency

	dependencies {
	         implementation 'com.github.HridaySarma:EZIPSMSAPI:1.0.0'
	}

### How to use the library

``` diff
  /// Create an instance of SMS Client
    private SMSClient smsClient ;

  /// Get your authentication key and paste it here.
    private String AuthKey = "YOUR AUTH KEY";

///Add your values in any way you want
    private String Message = "";
    private String PhoneNumber = "";
    private String SenderId = "DEMOOS";
    private String RouteId = "1";
    private String SMSContentType = "english";
    private Response response;
    private String url;
    private OkHttpClient client = new OkHttpClient();
```

Do your backend and when you get the phone number and message cexecute the AsyncTask to run it in background and send request 👍 

``` diff
   private  class  AsyncCaller extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }
        @Override
        protected Void doInBackground(Void... params) {


            // Get the string by calling the get url and add the user info //
            url = smsClient.getUrl(AuthKey,Message,SenderId,PhoneNumber,RouteId,SMSContentType);
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Cache-Control", "no-cache")
                    .build();

            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (response.isSuccessful()){
                Toast.makeText(MainActivity.this, "SMS SENT", Toast.LENGTH_SHORT).show();
            }
            pdLoading.dismiss();
        }

    }

```
## NOTE 
When using it in production do not keep the authentication key hardcoded in the  app . Apps can easily be decompiled and your authentication key will be exposed. Call the Auth-Key from the server to prevent exploitation of your account.
