import { bindActionCreators } from 'redux';
import { path } from 'ramda';
import { connect } from '../../helpers/connect';
import { signOut } from '../../actions/user';
import { setToPlay } from '../../actions/content';
import { IStore } from '../../store';
import { IUser } from '../../reducers/reducers';
import { getCurrentUser } from '../../actions/user';
import { getCities, getCountries } from '../../actions/content-async';
import Header from './Header';

const currentUserSelector = (state: IStore): IUser | null => path(['user', 'current'], state);

export default (connect(
  (state: IStore) => ({
    current: currentUserSelector(state),
  }),
  (dispatch) =>
    bindActionCreators(
      {
        getCurrentUser,
        signOut,
        setToPlay,
        getCities,
        getCountries,
      },
      dispatch
    )
) as any)(Header);
