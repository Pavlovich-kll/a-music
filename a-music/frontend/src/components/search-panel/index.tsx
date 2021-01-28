import React from 'react';
import AsyncSelect from 'react-select/async';
import { customStyles } from './styles';
import HTTP from '../../common/api';
import { SearchView } from './styles';
import { notification } from 'antd';

interface searchPanelProps {
  setToPlay?: any;
  addTrackInPlayList?: any;
  isPlayList?: boolean;
  createPlayListTracks?: any;
  isEditPlayList?: boolean;
  setNullSearch?: any;
  playlistDataObj?: any;
  currentData?: any;
}

interface searchPanelState {
  inputValue: any;
  searchSelection: any;
}

interface searchingItem {
  id: string;
  author: string;
  title: string;
  type: string;
  track_id: string;
  cover_id?: string;
  __v?: number;
  genres?: string;
  updatedAt?: string;
  createdAt?: string;
  likes?: number;
  album?: string;
}

const openNotification = () => {
  notification.error({
    message: 'This track is already in the playlist',
    style: {
      width: 600,
      marginLeft: 335 - 600,
      marginTop: 50,
    },
    duration: 0,
  });
};

class SearchPanel extends React.Component<searchPanelProps, searchPanelState> {
  constructor(props: searchPanelProps = { isPlayList: false, isEditPlayList: false }) {
    super(props);
    this.state = {
      inputValue: '',
      searchSelection: '',
    };
  }

  select: any;

  filter = async (inputValue: string) => {
    try {
      const searchResult: searchingItem[] = this.props.currentData
        ? this.props.currentData
        : await HTTP.get(`/music-service/search?searching=${inputValue}`);

      if (this.props.isPlayList || this.props.isEditPlayList) {
        this.props.setNullSearch(!searchResult.length);
      }
      return searchResult
        .map(({ title, author, ...track }: searchingItem) => ({
          ...track,
          title,
          author,
          label: `${title} - ${author}`,
        }))
        .filter(({ label }) => label.toLowerCase().includes(inputValue.toLowerCase()));
    } catch (err) {}
  };

  handleChange = (target: any) => {
    const {
      isPlayList,
      setToPlay,
      isEditPlayList,
      createPlayListTracks,
      playlistDataObj,
      addTrackInPlayList,
      currentData, //текущий плейлист
    } = this.props;
    if (target && !isPlayList) {
      this.setState((state) => ({
        inputValue: target.label,
        searchSelection: target,
      }));
      setToPlay(target.track_id);
    }
    if (target && (isPlayList || isEditPlayList)) {
      this.setState((state) => ({
        inputValue: target.label,
        searchSelection: target,
      }));
      setToPlay(target.track_id);
      const find = ({ _id }: any) => target._id === _id;
      const isInAddTracksPlaylist = createPlayListTracks.find(find);
      const isInActualPlaylist = Boolean(playlistDataObj?.tracks.find(find));
      if (!isInAddTracksPlaylist && !isInActualPlaylist) {
        addTrackInPlayList(target);
      } else {
        openNotification();
      }
    }
    if (target === null) {
      this.setState((state) => ({
        searchSelection: '',
        inputValue: '',
      }));
    }
  };

  handleInputChange = (inputValue: any, { action }: any) => {
    action === 'input-change' && this.setState({ inputValue });
    inputValue === '' && action !== 'menu-close' && action !== 'input-blur' && this.handleChange(null);
  };
  render() {
    const { inputValue, searchSelection } = this.state;
    return (
      <SearchView>
        <AsyncSelect
          styles={customStyles}
          cacheOptions
          loadOptions={this.filter}
          onChange={this.handleChange}
          isClearable
          autoFocus
          placeholder="Search..."
          onInputChange={this.handleInputChange}
          inputValue={inputValue}
          value={searchSelection}
        />
      </SearchView>
    );
  }
}

export default SearchPanel;
