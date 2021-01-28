import { bindActionCreators } from 'redux';
import { connect } from '../../helpers/connect';
import { uploadMusic } from '../../actions/content-async';
import SendMusic from './send-music';

export default connect(null, (dispatch) =>
  bindActionCreators(
    {
      uploadMusic,
    },
    dispatch
  )
)(SendMusic);
