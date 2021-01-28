import React, { useEffect } from 'react';
import { getAllMusic } from '../../actions/content-async';
import ContentCardView from './styles';
import Card from './card';
import Gallery from '../gallery/index';
import { ITrack } from '../../reducers/reducers';
import { IStore } from '../../store';
import { useDispatch, useSelector } from 'react-redux';

const ContentCard = () => {
  const dispatch = useDispatch();
  const {
    content: { isPlay, audio, current, currentPlayList },
  } = useSelector(({ content }: IStore) => ({ content }));

  const showDeleteBtn = (track_id: string) => {
    return (
      currentPlayList.length > 0 &&
      (current.track_id !== track_id || currentPlayList.length === 1) &&
      currentPlayList.findIndex((_: ITrack) => track_id === _.track_id) >= 0
    );
  };

  useEffect(() => {
    dispatch(getAllMusic());
  }, []);

  return (
    <>
      <Gallery />
      <ContentCardView>
        {audio.map(({ track_id, ...props }: ITrack) => (
          <Card
            key={track_id}
            track_id={track_id}
            {...props}
            trackData={{ track_id, ...props }}
            isPlaying={current?.track_id === track_id && isPlay}
            showDeleteBtn={showDeleteBtn(track_id)}
          />
        ))}
      </ContentCardView>
    </>
  );
};

export default ContentCard;
