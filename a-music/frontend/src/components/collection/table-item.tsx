import React from 'react';
import { IconButton } from '@material-ui/core';
import PlayArrowIcon from '@material-ui/icons/PlayArrow';
import PauseIcon from '@material-ui/icons/Pause';
import { ITrack } from '../../reducers/reducers';
import { setToPlay, setIsPlay, clearPlaylist, addToPlaylist } from '../../actions/content';
import { useDispatch } from 'react-redux';
import { LikeButton } from '../../ui/player/player-like';
import CloseIcon from '@material-ui/icons/Close';

interface ITableItem {
  track: ITrack;
  isPlaying: boolean;
  track_number: number;
  handleRemove(id: string | number): void;
}

export const TableItem = ({ track, isPlaying, track_number, handleRemove }: ITableItem) => {
  const { track_id, likes, title, author, album, genres } = track;
  const dispatch = useDispatch();

  const handleTogglePlay = () => {
    if (isPlaying) {
      dispatch(setIsPlay(false));
    } else {
      dispatch(clearPlaylist());
      dispatch(addToPlaylist([track]));
      dispatch(setToPlay(track.track_id));
      dispatch(setIsPlay(true));
    }
  };

  return (
    <tr>
      <td data-label="#">{track_number}</td>
      <td data-label="">
        <LikeButton trackId={track_id} likes={likes} isRenderCounter={false} />
      </td>
      <td data-label="title">{title}</td>
      <td data-label="author">{author}</td>
      <td data-label="album">{album}</td>
      <td data-label="genres">{genres.join(' ')}</td>
      <td data-label="likes">{likes}</td>
      <td data-label="listen now">
        <IconButton onClick={handleTogglePlay} className="play-icon" size="small">
          {isPlaying ? <PauseIcon fontSize="inherit" /> : <PlayArrowIcon fontSize="inherit" />}
        </IconButton>
      </td>
      <td data-label="edit">
        <IconButton className="delete-icon" size="small" onClick={() => handleRemove(track_id)}>
          <CloseIcon />
        </IconButton>
      </td>
    </tr>
  );
};
