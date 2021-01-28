import React, { useEffect, useState } from 'react';
import { Slider } from '@material-ui/core';
import { addSeconds, format } from 'date-fns';

interface IPlayerTimeProps {
  currentTime: number;
  duration: number;
  onHandleChangeTime(event: React.ChangeEvent<{}>, value: any): void;
}

const DEFAULT_TIME: string = '00:00';

export const formattedTime = (seconds: number): string => {
  try {
    const helperDate = addSeconds(new Date(0), seconds);
    return format(helperDate, 'mm:ss');
  } catch (e) {
    return DEFAULT_TIME;
  }
};

export default ({ currentTime, duration, onHandleChangeTime }: IPlayerTimeProps) => {
  const [time, setTime] = useState<number>(currentTime);
  const [isRewinding, setIsRewinding] = useState<boolean>(false);

  const handleChangeTime = (event: React.ChangeEvent<{}>, newTime: any) => {
    setIsRewinding(true);
    setTime(newTime);
    if (event.type !== 'mousemove' && event.type !== 'mousedown') {
      onHandleChangeTime(event, newTime);
      setIsRewinding(false);
    }
  };

  useEffect(() => {
    if (!isRewinding) {
      setTime(currentTime);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [currentTime]);

  return (
    <div className="playerWrapper" data-testid="player-time">
      <div className="play-control">
        <Slider
          color="secondary"
          value={time}
          min={0}
          step={0.01}
          max={duration}
          onChangeCommitted={handleChangeTime}
          onChange={handleChangeTime}
          data-testid="player-slider"
          className="player-slider"
        />
        <span className="time">
          {formattedTime(currentTime)}/{formattedTime(duration)}
        </span>
      </div>
    </div>
  );
};
