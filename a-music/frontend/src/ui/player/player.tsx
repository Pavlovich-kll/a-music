/* eslint-disable react-hooks/exhaustive-deps */
import React, { memo, useState, useRef, useCallback, useEffect } from 'react';
import PlayerControl from './player-control';
import PlayerTime from './player-time';
import ActualPlaylist from './actual-playlist';
import AdditionalControlsPanel from './player-additional-controls-panel';
import PlayerView, { ContainerWrapper } from './styles';
import { useDispatch } from 'react-redux';
import { setIsPlay, setIsRandom, setIsLoop } from '../../actions/content';
import CurrentTrack from '../../components/current-track';
import { ArrowTop, ArrowDown } from './svg/player-additional-controls-panel-svg';
import TrackList from '../../components/track-list';
import { GET_PLAY_LIST_FILE } from '../../constants';

interface IPlayerProps {
  onChangeTrack(value: string): Function;

  isNext: boolean;
  resetTime: object;
  likes: number;
  src: string;
  isPlay: boolean;
  isRandom: boolean;
  isLoop: boolean;
}

const Player = ({ onChangeTrack, likes, src, isPlay, resetTime, isNext, isRandom, isLoop }: IPlayerProps) => {
  const [duration, setDuration] = useState<number>(0);
  const [time, setTime] = useState<number>(0);
  const [showPlaylist, setShowPlaylist] = useState<boolean>(false);

  const [volume, setVolume] = useState<number>(10);

  const dispatch = useDispatch();
  const audioReference: any = useRef();

  const timeUpdate = ({ target: { currentTime, duration } }: any) => {
    setDuration(duration);
    setTime(currentTime);

    if (currentTime !== duration || !isPlay) return;
    return isNext || isLoop || isRandom ? onChangeTrack('next')() : dispatch(setIsPlay(false));
  };

  const handleTogglePlay = () => {
    dispatch(setIsPlay(!isPlay));
  };

  const handleOpenPlaylist = () => {
    setShowPlaylist(true);
  };

  const handleClosePlaylist = () => {
    setShowPlaylist(false);
  };

  const handleChangeTime = (event: React.ChangeEvent<{}>, value: number) => {
    const { current } = audioReference;
    current.currentTime = value;
  };

  const handleChangeVolume = useCallback(
    (value: number) => {
      const { current } = audioReference;
      setVolume(value);
      current.volume = value / 100;
    },
    [audioReference]
  );

  const handleChangeState = (property: string) => () => {
    if (property === 'random') {
      dispatch(setIsRandom(!isRandom));
    }
    if (property === 'loop') {
      dispatch(setIsLoop(!isLoop));
    }
  };

  useEffect(() => {
    // Сбрасывает время трека
    const { current } = audioReference;
    current.currentTime = 0;
  }, [resetTime]);

  useEffect(() => {
    const { current } = audioReference;
    current.addEventListener('timeupdate', timeUpdate);
    return () => {
      current.removeEventListener('timeupdate', timeUpdate);
    };
  }, [src, isNext, isLoop, isRandom]);

  useEffect(() => {
    // fix ошибки при переключении трека
    const { current } = audioReference;
    isPlay ? current.play() : current.pause();
  }, [src, isPlay, resetTime]);

  return (
    <>
      <ContainerWrapper>
        <PlayerView data-testid="player">
          <CurrentTrack />
          <PlayerControl isPlaying={isPlay} onTogglePlay={handleTogglePlay} onChangeTrack={onChangeTrack} />
          <PlayerTime
            currentTime={time}
            duration={duration}
            onHandleChangeTime={handleChangeTime}
            data-testid="player-time"
          />
          <AdditionalControlsPanel
            state={{ volume, likes }}
            onChangeState={handleChangeState}
            onChangeVolume={handleChangeVolume}
            isRandom={isRandom}
            loop={isLoop}
          />
          {!showPlaylist && (
            <div className="button-playlist" onClick={handleOpenPlaylist}>
              <ArrowTop />
            </div>
          )}
          {showPlaylist && (
            <div className="btn-playlist-containter">
              <ActualPlaylist content={<TrackList />} toClose={handleClosePlaylist} />
              <div className="button-playlist" onClick={handleClosePlaylist}>
                <ArrowDown />
              </div>
            </div>
          )}
        </PlayerView>
      </ContainerWrapper>
      <audio ref={audioReference} src={`${GET_PLAY_LIST_FILE}/${encodeURIComponent(src)}`} preload="metadata" />
    </>
  );
};

export default memo(Player);
