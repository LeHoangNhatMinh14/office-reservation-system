import styles from './Modal.module.css';

const Modal = ({ isOpen, onClose, title, children }) => {
    if (!isOpen) return null;

    return (
        <div className={styles.modalOverlay} onClick={onClose}>
            <div className={styles.modalContent} onClick={(e) => e.stopPropagation()}>
                <div className={styles.modalHeader}>
                    <h2 className={styles.modalTitle}>{title}</h2>
                    <button className={styles.closeButton} onClick={onClose}>x</button>
                </div>
                {children}
            </div>
        </div>
    );
};
//test
export default Modal;

