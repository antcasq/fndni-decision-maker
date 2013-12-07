package pt.cyberRabbit.server;

import java.util.List;

import pt.cyberRabbit.server.domain.Inquiry;
import pt.cyberRabbit.server.domain.User;
import pt.cyberRabbit.shared.domain.exceptions.DomainException;
import pt.cyberRabbit.shared.dto.ElectorDTO;
import pt.cyberRabbit.shared.dto.InquiryResultSummaryDTO;
import pt.cyberRabbit.shared.dto.InquiryStatusDTO;
import pt.cyberRabbit.shared.dto.UserDTO;

public class DecisionMakerFacade {

	public static UserDTO findUserByUsername(final String username) {
		if (username == null) {
			throw new IllegalArgumentException("username.required");
		}

		try {
			ServerHelper.init(true);

			final User user = User.findByUsername(username);
			if (user != null) {
				return user.buildDTO();
			}
			// } catch (Throwable t) {
			// t.printStackTrace();
		} finally {
			ServerHelper.getInstance().releaseResources();
		}
		return null;
	}

	public static List<UserDTO> getAllUsers(final String username) {
		if (username == null) {
			throw new IllegalArgumentException("username.required");
		}

		try {
			ServerHelper.init(true);

			final List<UserDTO> users = User.getAllWithoutRegistriesDTO();
			if (users != null) {
				return users;
			}
			// } catch (Throwable t) {
			// t.printStackTrace();
		} finally {
			ServerHelper.getInstance().releaseResources();
		}
		return null;
	}

	public static List<ElectorDTO> getAllElectors(final String username,
			final String inquiryCode) {
		if (username == null) {
			throw new IllegalArgumentException("username.required");
		}

		if (inquiryCode == null) {
			throw new IllegalArgumentException("inquiryCode.required");
		}

		try {
			ServerHelper.init(true);

			final User user = User.findByUsername(username);
			if (user == null) {
				return null;
			}

			if (!user.isAdmin()) {
				throw new DomainException("error.admin.role.needed");
			}

			final Inquiry inquiry = Inquiry.findByInquiryCode(inquiryCode);
			if (inquiry == null) {
				return null;
			}

			final List<ElectorDTO> electors = inquiry.getElectors();
			if (electors != null) {
				return electors;
			}
			// } catch (Throwable t) {
			// t.printStackTrace();
		} finally {
			ServerHelper.getInstance().releaseResources();
		}
		return null;
	}

	public static InquiryStatusDTO getInquiryStatus(final String username,
			final String inquiryCode) {
		if (username == null) {
			throw new IllegalArgumentException("username.required");
		}

		if (inquiryCode == null) {
			throw new IllegalArgumentException("inquiryCode.required");
		}

		try {
			ServerHelper.init(true);

			final Inquiry inquiry = Inquiry.findByInquiryCode(inquiryCode);
			if (inquiry == null) {
				return null;
			}

			final InquiryStatusDTO inquiryStatus = inquiry.getInquiryStatus();
			if (inquiryStatus != null) {
				return inquiryStatus;
			}
			// } catch (Throwable t) {
			// t.printStackTrace();
		} finally {
			ServerHelper.getInstance().releaseResources();
		}
		return null;
	}

	public static InquiryResultSummaryDTO getInquiryResult(
			final String username, final String inquiryCode) {
		if (username == null) {
			throw new IllegalArgumentException("username.required");
		}

		if (inquiryCode == null) {
			throw new IllegalArgumentException("inquiryCode.required");
		}

		try {
			ServerHelper.init(true);

			final Inquiry inquiry = Inquiry.findByInquiryCode(inquiryCode);
			if (inquiry == null) {
				return null;
			}

			final InquiryResultSummaryDTO inquiryResultSummary = inquiry
					.getInquiryResult();
			if (inquiryResultSummary != null) {
				return inquiryResultSummary;
			}
			// } catch (Throwable t) {
			// t.printStackTrace();
		} finally {
			ServerHelper.getInstance().releaseResources();
		}
		return null;
	}

	public static void publishInquiryResult(final String username,
			final String inquiryCode) {
		if (username == null) {
			throw new IllegalArgumentException("username.required");
		}

		if (inquiryCode == null) {
			throw new IllegalArgumentException("inquiryCode.required");
		}

		try {
			ServerHelper.init(true);

			final User user = User.findByUsername(username);
			if (user == null) {
				return;
			}

			if (!user.isAdmin()) {
				throw new DomainException("error.admin.role.needed");
			}

			final Inquiry inquiry = Inquiry.findByInquiryCode(inquiryCode);
			if (inquiry == null) {
				return;
			}

			inquiry.publishResult();
			// } catch (Throwable t) {
			// t.printStackTrace();
		} finally {
			ServerHelper.getInstance().releaseResources();
		}
	}

	public static void answerInquiry(final String username,
			final String inquiryCode, final List<Long> answers) {
		if (username == null) {
			throw new IllegalArgumentException("username.required");
		}

		if (inquiryCode == null) {
			throw new IllegalArgumentException("inquiryCode.required");
		}

		if (answers == null || answers.isEmpty()) {
			throw new IllegalArgumentException("answers.required");
		}

		try {
			ServerHelper.init(true);

			final User user = User.findByUsername(username);
			if (user == null) {
				return;
			}

			user.answerInquiry(inquiryCode, answers);
			// } catch (Throwable t) {
			// t.printStackTrace();
		} finally {
			ServerHelper.getInstance().releaseResources();
		}
	}

}
