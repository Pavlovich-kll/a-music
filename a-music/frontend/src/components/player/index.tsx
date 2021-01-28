import React, { useEffect, useState } from 'react';
import { head, last, pathOr } from 'ramda';
import { IStore } from '../../store';
import { updateCurrentTrack } from '../../actions/content';
import PlayerComponent from '../../ui/player';
import { useDispatch, useSelector } from 'react-redux';

const Player = () => {
  const dispatch = useDispatch();
  const {
    content: { isPlay, current, currentPlayList, isRandom, isLoop },
  } = useSelector(({ content }: IStore) => ({ content }));

  const track_id: string = pathOr('', ['track_id'], current);
  const likes: number = pathOr(0, ['likes'], current);

  const [currentTrack, setCurrentTrack] = useState({ value: 0 });
  const [resetTime, setResetTime] = useState<object>({});
  useEffect(() => {
    // Должен проигрывать новый трек, когда currentTrack меняется
    const { value } = currentTrack;
    dispatch(updateCurrentTrack(currentPlayList[value]));
  }, [currentTrack]);

  useEffect(() => {
    // Надо обновлять currentTrack когда трек меняется.
    setCurrentTrack({ value: currentPlayList.findIndex((track) => track.track_id === current.track_id) });
  }, [current, currentPlayList]);

  const _handleClickNext = () => {
    setCurrentTrack(({ value }) => {
      if (current.track_id === last(currentPlayList).track_id) return { value: 0 };
      return { value: value + 1 };
    });
  };

  const _handleClickPrev = () => {
    setCurrentTrack(({ value }) => {
      if (current.track_id === head(currentPlayList).track_id) return { value: currentPlayList.length - 1 };
      return { value: value - 1 };
    });
  };

  const handleChangeTrack = (value: string) => (clicked: any) => {
    const randomValue: number = Math.floor(Math.random() * currentPlayList.length);
    if (currentPlayList.length <= 1 || (isLoop && !clicked) || (isRandom && currentTrack.value === randomValue))
      return setResetTime({});

    if (isRandom) return setCurrentTrack(() => ({ value: randomValue }));

    const trackMap: { [key: string]: () => void } = {
      next: _handleClickNext,
      prev: _handleClickPrev,
    };
    trackMap[value]();
  };
  return (
    <>
      <PlayerComponent
        onChangeTrack={handleChangeTrack}
        likes={likes}
        src={track_id}
        isPlay={isPlay}
        isRandom={isRandom}
        isLoop={isLoop}
        resetTime={resetTime}
        isNext={currentTrack.value + 1 < currentPlayList.length}
      />
    </>
  );
};

export default Player;
