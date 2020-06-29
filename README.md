
# The Block.Art CorDapp

Decentralized tokenisation platform, which consist from 4 independent nodes and 3 webservers for them. This platform allows to create digital alias (token) of art object, transfer right ownerships to other participants and brief history of owners for art objects.
For more details, check presentation (https://github.com/Pe4enable/ArtOfBlockchain/blob/master/BlockArt.pdf)

# Instructions for setting up

Make build file
./gradlew wrapper 

Build project
./gradlew deployNodes 

Run nodes and webservices
./java-source/build/nodes/runnodes


# Using the CorDapp via the web front-end

In your favourite web browser, navigate to:

1. PartyA: `http://localhost:10007`
2. PartyB: `http://localhost:10010`
3. PartyC: `http://localhost:10013`

You'll see a basic page, listing all the API end-points and static web content. Click on the "art" link under 
"static web content". The dashboard shows you a number of things:

1. All issued art objects to date
2. A button to issue a new ar object
3. A button to self issue cash
4. A refresh button

## Issue an art object

1. Click on the "create artObject" button.
2. Select the counterparty, enter in the currency (GBP) and the amount, 1000, url of image of artObject
3. Click create artObject
4. Wait for the transaction confirmation
5. Click anywhere
6. The UI should update to reflect the new art object.
7. Navigate to the counterparties dashboard. You should see the same art object there. The party names show up as random public keys as they are issued confidentially. Currently the web API doesn't resolve the party names.

## Self issue some cash

From the counterparty UI:

1. Click the issue cash button
2. Enter a currency (GBP) and amount, 10000
3. Click "issue cash"
4. Wait for the transaction confirmation
5. click anywhere
6. You'll see the "Cash balances" section update

## Transfer an art object

From the art object owner UI:

1. Click the "Transfer" button for the art object you previously just issued.
2. Choose counterparty
3. Press the "Transfer" button
4. Wait for the confirmation
5. Click anywhere
6. You'll see that art object is disapered
7. Navigate to the counterparty UI, click refresh, you'll see that transfered art object


# TODO

1. Resolve party names for the web front-end.
2. Replace the Corda web server with a reactive spring boot web server


Feel free to submit a PR.
