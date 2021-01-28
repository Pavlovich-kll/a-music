import React from 'react';
import CurrentTrackView from './styles';

interface IProps {
  cover_id?: string;
  title?: string;
  author?: string;
}

const TrackCover = ({ cover_id, title = 'title', author = 'author' }: IProps) => (
  <CurrentTrackView>
    {cover_id && <img src={`${process.env.API_URL}music-service/content/file/${cover_id}`} alt={title} />}
    <div className="container-text">
      <span className="span-title">{title}</span>
      <span className="span-author">{author}</span>
    </div>
  </CurrentTrackView>
);

export default TrackCover;
