import Modal from './Modal.jsx';
import RoomDesigner from "../design/RoomDesigner.jsx";

const RoomOverviewModal = ({ isOpen, onClose, capacity }) => {


    return (
        <Modal isOpen={isOpen} onClose={onClose} title="Room Overview">
            <RoomDesigner />
        </Modal>
    );
};

export default RoomOverviewModal;

