"use strict";

angular.module('demoAppModule').controller('CreateArtObjectModalCtrl', function($http, $uibModalInstance, $uibModal, apiBaseURL, peers, refreshCallback) {
    const createArtObjectModal = this;

    createArtObjectModal.peers = peers;
    createArtObjectModal.form = {};
    createArtObjectModal.formError = false;

    /** Validate and create an ArtObject. */
    createArtObjectModal.create = () => {
        if (invalidFormInput()) {
            createArtObjectModal.formError = true;
        } else {
            createArtObjectModal.formError = false;

            const amount = createArtObjectModal.form.amount;
            const currency = createArtObjectModal.form.currency;
            const party = createArtObjectModal.form.counterparty;

            $uibModalInstance.close();

            // We define the ArtObject creation endpoint.
            const issueArtObjectEndpoint =
                apiBaseURL +
                `issue-artObject?amount=${amount}&currency=${currency}&party=${party}`;

            // We hit the endpoint to create the ArtObject and handle success/failure responses.
            $http.get(issueArtObjectEndpoint).then(
                   (result) => { createArtObjectModal.displayMessage(result); refreshCallback();},
                   (result) => { createArtObjectModal.displayMessage(result); refreshCallback();}
            );
        }
    };

    /** Displays the success/failure response from attempting to create an ArtObject. */
    createArtObjectModal.displayMessage = (message) => {
        const createArtObjectMsgModal = $uibModal.open({
            templateUrl: 'createArtObjectMsgModal.html',
            controller: 'createArtObjectMsgModalCtrl',
            controllerAs: 'createArtObjectMsgModal',
            resolve: {
                message: () => message
            }
        });

        // No behaviour on close / dismiss.
        createArtObjectMsgModal.result.then(() => {}, () => {});
    };

    /** Closes the ArtObject creation modal. */
    createArtObjectModal.cancel = () => $uibModalInstance.dismiss();

    // Validates the ArtObject.
    function invalidFormInput() {
        return isNaN(createArtObjectModal.form.amount) || (createArtObjectModal.form.counterparty === undefined);
    }
});

// Controller for the success/fail modal.
angular.module('demoAppModule').controller('createArtObjectMsgModalCtrl', function($uibModalInstance, message) {
    const createArtObjectMsgModal = this;
    createArtObjectMsgModal.message = message.data;
});