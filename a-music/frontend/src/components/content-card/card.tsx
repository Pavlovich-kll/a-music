import React from 'react';
import { useDispatch } from 'react-redux';
import { ITrack } from '../../reducers/reducers.d';
import { ContentCover } from './styles';
import { setIsPlay, setToPlay, clearPlaylist, addToPlaylist, removeFromPlaylist } from '../../actions/content';
import { IconButton } from '@material-ui/core';
import PlayArrowIcon from '@material-ui/icons/PlayArrow';
import PauseIcon from '@material-ui/icons/Pause';
import AddIcon from '@material-ui/icons/Add';
import RemoveIcon from '@material-ui/icons/Remove';
import { Progressive } from './progressiveImage';

interface ICardProps extends ITrack {
  trackData: ITrack;
  isPlaying: boolean;
  showDeleteBtn: boolean;
}

const Card = ({ track_id, cover_id, trackData, isPlaying, showDeleteBtn }: ICardProps) => {
  const dispatch = useDispatch();

  const handleClickAdd = () => dispatch(addToPlaylist([trackData]));

  const handleClickRemove = () => dispatch(removeFromPlaylist(track_id));

  const handleTogglePlay = () => {
    if (isPlaying) {
      dispatch(setIsPlay(false));
    } else {
      dispatch(clearPlaylist());
      dispatch(addToPlaylist([trackData]));
      dispatch(setToPlay(track_id));
      dispatch(setIsPlay(true));
    }
  };

  return (
    <ContentCover>
      <div className="progressive">
        <Progressive cover={cover_id} />
      </div>
      <div className="hoverable">
        <div className="hoverable__description-block-title">
          <p className="author">{trackData.author}</p>
        </div>
        <div className="hoverable__control-block">
          <span className="hoverable__control-wrapper">
            <IconButton data-testid="add-track" onClick={handleClickAdd}>
              <AddIcon fontSize="large" />
            </IconButton>
          </span>
          <span className="hoverable__control-wrapper">
            <IconButton onClick={handleTogglePlay} color="secondary" data-testid="toggle-play">
              {isPlaying ? <PauseIcon fontSize="large" /> : <PlayArrowIcon fontSize="large" />}
            </IconButton>
          </span>
          <span className="hoverable__control-wrapper">
            {showDeleteBtn && (
              <IconButton onClick={handleClickRemove} data-testid="remove-track">
                <RemoveIcon fontSize="large" />
              </IconButton>
            )}
          </span>
        </div>
        <div className="hoverable__description-block">
          <p className="album">{trackData.album}</p>
          <p className="title">{trackData.title}</p>
        </div>
      </div>
    </ContentCover>
  );
};

export default Card;
