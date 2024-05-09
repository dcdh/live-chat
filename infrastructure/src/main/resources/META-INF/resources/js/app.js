import {PeerConnection} from "./peer-connection.js";

const peerConnection = new PeerConnection({
    onLocalMedia: stream => document.getElementById("localVideo").srcObject = stream,
    onRemoteMedia: stream => document.getElementById("remoteVideo").srcObject = stream,
    onStateChange: state => {
        document.body.dataset.state = state;
    }
});

document.getElementById("startPairing").addEventListener("click", async () => {
    peerConnection.setState("CONNECTING");
    peerConnection.sdpExchange.send(JSON.stringify({pairingStep: "PAIRING_START"}))
});

document.getElementById("abortPairing").addEventListener("click", () => {
    peerConnection.sdpExchange.send(JSON.stringify({pairingStep: "PAIRING_ABORT"}))
    peerConnection.disconnect("LOCAL");
})

document.getElementById("leavePairing").addEventListener("click", () => {
    peerConnection.sendBye();
});

window.addEventListener("beforeunload", () => {
    if (peerConnection.state === "CONNECTED") {
        peerConnection.sendBye();
    }
});
