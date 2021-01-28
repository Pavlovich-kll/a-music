import React, { useCallback, useEffect, useState } from 'react';
import IconButton from '@material-ui/core/IconButton';
import StarBorderIcon from '@material-ui/icons/StarBorder';
import { useSelector } from 'react-redux';
import StarIcon from '@material-ui/icons/Star';
import { motion } from 'framer-motion';
import { IStore } from '../../store';

export const FavoriteButton: React.FC = () => {
  const [favoriteTracksData, setFavoriteTrack] = useState(() => {
    const favoriteTracks = localStorage.getItem('favoriteTracks');
    return JSON.parse(favoriteTracks || '[]');
  });
  const currentTrackId: string = useSelector(({ content: { current } }: IStore) => current && current.track_id);

  const checkTrackId = useCallback(
    (trackData: string[]) => trackData.some((trackId: string) => trackId === currentTrackId),
    [currentTrackId]
  );

  const handleToggleToFavorite = (): void => {
    if (checkTrackId(favoriteTracksData)) {
      const newFavoriteTracksData: null | Array<string> = favoriteTracksData.filter(
        (trackId: string) => trackId !== currentTrackId
      );
      setFavoriteTrack(newFavoriteTracksData);
    } else {
      setFavoriteTrack((prev: Array<string>) => [currentTrackId, ...prev]);
    }
  };

  useEffect(() => {
    localStorage.setItem('favoriteTracks', JSON.stringify(favoriteTracksData));
  }, [favoriteTracksData]);

  return (
    <IconButton data-testid="icon-button" className="favorite-button" onClick={handleToggleToFavorite}>
      <motion.div whileHover={{ scale: 1.3 }}>
        {checkTrackId(favoriteTracksData) ? (
          <StarIcon data-testid="star-icon" />
        ) : (
          <StarBorderIcon data-testid="star-border-icon" />
        )}
      </motion.div>
    </IconButton>
  );
};
