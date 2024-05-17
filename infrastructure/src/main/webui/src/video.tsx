// https://dev.to/adamwojnicki/react-autoplay-solution-2bkl
// https://github.com/facebook/react/issues/11163#issuecomment-1152626485
import { VideoHTMLAttributes, useEffect, useRef } from 'react'

type PropsType = VideoHTMLAttributes<HTMLVideoElement> & {
  srcObject?: MediaStream
}

export default function Video({ srcObject, ...props }: PropsType) {
  const refVideo = useRef<HTMLVideoElement>(null)

  useEffect(() => {
    if (!refVideo.current) return
    if (srcObject) {
      refVideo.current.srcObject = srcObject
    }
  }, [srcObject])

  return <video ref={refVideo} {...props} autoPlay playsInline />
};
