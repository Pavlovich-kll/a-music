import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { setLikeToTrack } from '../../actions/content-async';
import { LikeButtonWrapper } from './styles';
import { LikeSVG } from './svg/player-additional-controls-panel-svg';
import { IconButton } from '@material-ui/core';
import { IStore } from '../../store';

interface ILikesProps {
  trackId: string;
  likes: number;
  isRenderCounter: boolean;
}

export const LikeButton: React.FC<ILikesProps> = ({ trackId, likes, isRenderCounter }: ILikesProps) => {
  const currentTrackId: string = useSelector(({ content: { current } }: IStore) => current && current.track_id);

  const dispatch = useDispatch();

  const handleToLike = async () => {
    await dispatch(setLikeToTrack(trackId || currentTrackId));
  };

  return (
    <LikeButtonWrapper data-testid="like-component">
      <IconButton data-testid="like-button" className="like-button" onClick={handleToLike}>
        <LikeSVG />
      </IconButton>
      {isRenderCounter && (
        <span data-testid="like-counter" className={'span-likes'}>
          {likes}
        </span>
      )}
    </LikeButtonWrapper>
  );
};
