import { Button, Col, Container, Row } from 'react-bootstrap';
import Video from './video';
import { useEffect, useState } from 'react';
import { useAppDispatch, useAppSelector } from './app/hooks';
import { cancelMatchMakingFromEndpoint, findAStrangerFromEndpoint, leaveMatchMakingFromEndpoint, selecOneToOneMatching } from './one-to-one-matching.slice';
import { useSSE } from 'react-hooks-sse';
import { MatchedStatus } from './api/model';

interface AppProps {
  requestedBy: string;
};

interface MatchedOneToOneLifecycle {
  initiatedBy: string;
  joinedBy: string;
  matchedStatus: MatchedStatus
};

export const App: React.FC<AppProps> = (props) => {
  const dispatch = useAppDispatch();
  const selectedOneToOneMatching = useAppSelector(selecOneToOneMatching);

  const [localStream, setLocalStream] = useState<MediaStream | undefined>(undefined);
  const matchedOneToOneLifecycle: MatchedOneToOneLifecycle | undefined = useSSE<MatchedOneToOneLifecycle | undefined>('MatchedOneToOneLifecycle', undefined);

  useEffect(() => {
    const startLocalStream = async () => {
      return await navigator.mediaDevices.getUserMedia({video: true, audio: true});
    }
    startLocalStream().then(response => {
      setLocalStream(response);
    });
    if (matchedOneToOneLifecycle?.matchedStatus === MatchedStatus.MATCHED) {
      if (matchedOneToOneLifecycle!.initiatedBy === props.requestedBy) {
        console.log("initialized by me: Push an offer");
      } else {
        console.log("not initialized by me wainting for spd answer");
      }
    } else if (matchedOneToOneLifecycle?.matchedStatus === MatchedStatus.LEAVED_INITIATOR || 
                matchedOneToOneLifecycle?.matchedStatus === MatchedStatus.LEAVED_STRANGER) {
      console.log("LEAVED !!!!");
    }
  }, [setLocalStream, matchedOneToOneLifecycle])

  return (
    <Container>
      <Row>
        <Col className='remote'>
          <Video id="remoteVideo"></Video>
        </Col>
        <Col className='local'>
          <Video id="localVideo" srcObject={localStream}></Video>
        </Col>
      </Row>
      <Row>
        <Col>
          {
            selectedOneToOneMatching.canFindAStranger
            &&
              <><Button id='findAStranger' variant="primary" onClick={() => {
                dispatch(findAStrangerFromEndpoint(props.requestedBy))
              }}>Find stranger</Button>  </>
          }
          {
            selectedOneToOneMatching.canCancel
            &&
              <><Button id='cancelMatchMaking' variant="danger" onClick={() => {
                dispatch(cancelMatchMakingFromEndpoint(props.requestedBy))
              }}>Cancel</Button>  </>
          }
          {
            selectedOneToOneMatching.canLeave
            &&
              <Button id='leaveMatchMaking' variant="danger" onClick={() => {
                dispatch(leaveMatchMakingFromEndpoint(props.requestedBy))
              }}>Disconnect</Button>
          }
        </Col>
      </Row>
    </Container>
  );
};

export default App;