import tweepy as tw

# # Read API keys from a file
# with open('NAME OF YOUR FILE', 'r') as arquivo: #'arquivo' is a var name, u can change
#   consumer_key = arquivo.readline().strip('\n')
#   consumer_secret = arquivo.readline().strip('\n')
#   access_toekn = arquivo.readline().strip('\n')
#   access_token_secret = arquivo.readline().strip('\n')

consumer_key = 'EkcvtmIaJ2q0ymf6mzmFsONAc'
consumer_secret = 'DkREjEZkyJnsqzfHm8p4PCk3pkfhVnKDHRu0oVlwJmPNeSdXk0'
access_token = '1016876839434899456-1BtFDg3KCMiEkbwYPXjJrLRC63Phqc'
access_token_secret = 'XvaQKY7u7j88HR34mEK9eVtm3ZGlXxpTwSL1rYnA9211J'

auth = tw.OAuthHandler(consumer_key, consumer_secret) 
auth.set_access_token(access_token, access_token_secret)

api = tw.API(auth)
public_tweets = api.home_timeline()

for tweet in public_tweets:
    print(tweet.text)