import React from 'react';
import { BackArrowSVG, PlayArrowSVG, FrontArrowSVG, PauseSVG } from './svg/player-control-svg';

interface IPlayerControlProps {
  onTogglePlay(): void;
  onChangeTrack(value: string): any;
  isPlaying: boolean;
}

export default ({ onTogglePlay, onChangeTrack, isPlaying }: IPlayerControlProps) => (
  <div className="control">
    <div data-testid="prev-button" onClick={onChangeTrack('prev')}>
      <BackArrowSVG />
    </div>
    {isPlaying ? (
      <div data-testid="play-pause" onClick={onTogglePlay}>
        <PauseSVG />
      </div>
    ) : (
      <div data-testid="play-pause" onClick={onTogglePlay}>
        <PlayArrowSVG />
      </div>
    )}
    <div data-testid="next-button" onClick={onChangeTrack('next')}>
      <FrontArrowSVG />
    </div>
  </div>
);
