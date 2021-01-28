import React from 'react';

interface ICollectionHead {
  likes: number;
  title: string;
  description: string;
  totalLength: string;
  lastUpdate: string;
  trackCount: number;
  pic: string;
  authors: Array<string>;
}

export const CollectionHeader = ({
  likes,
  title,
  description,
  totalLength,
  lastUpdate,
  pic,
  trackCount,
  authors,
}: ICollectionHead) => {
  const getTimeDifference = (updateDate: string): number => {
    const today: number = Date.now();
    const gap = today - Date.parse(updateDate);
    return Math.floor(gap / 1000 / 3600 / 24);
  };

  return (
    <div className="collection__header">
      <div className="collection__header-image">
        <img src={`${process.env.API_URL}music-service/content/file/${pic}`} alt="playlist-title" />
      </div>
      <div className="collection__header-description">
        <div className="collection__header-description-title">
          <h1 className="collection__header-description-title-playlist-name">{title}</h1>
          <h2 className="collection__header-description-title-top-artist">{authors.join(', ')}</h2>
          <h2 className="collection__header-description-title-write-up">{description}</h2>
        </div>
        <div className="collection__header-description-summary">
          <p>
            {trackCount}&nbsp;{trackCount === 1 ? 'track' : 'tracks'}&ensp;&middot;&ensp;
            {totalLength}&ensp;&middot;&ensp;
            {likes}&nbsp;{likes === 1 ? 'like' : 'likes'}&ensp;&middot;&ensp; Updated&nbsp;
            {getTimeDifference(lastUpdate)}&nbsp;{getTimeDifference(lastUpdate) > 1 ? 'days' : 'day'}&nbsp;ago
          </p>
        </div>
      </div>
    </div>
  );
};
